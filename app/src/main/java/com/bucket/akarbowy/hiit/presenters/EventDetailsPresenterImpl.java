package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.adomain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.adomain.interactor.NoEmittingObserver;
import com.bucket.akarbowy.hiit.adomain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.EventDetailsFragment;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EventDetailsView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class EventDetailsPresenterImpl implements EventDetailsPresenter {

    private String mEventId;
    private EventDetailsView mEventDetailsView;
    private UseCase mGetEventDetailsUseCase, mEnrollUserUseCase;
    private EventDataMapper mEventDataMapper;

    @Inject
    EventDetailsPresenterImpl(@Named("eventDetails") UseCase getEventDetailsUseCase,
                              @Named("enrollUser") UseCase enrollUserUseCase,
                              EventDataMapper eventDataMapper) {
        mGetEventDetailsUseCase = getEventDetailsUseCase;
        mEnrollUserUseCase = enrollUserUseCase;
        mEventDataMapper = eventDataMapper;
    }

    public void setView(EventDetailsFragment eventDetailsView) {
        mEventDetailsView = eventDetailsView;
    }

    public void initialize(String eventId) {
        mEventId = eventId;
        loadDetails();
    }

    private void loadDetails() {
        mEventDetailsView.showViewWaiting();
        getEventDetails();
    }

    @Override
    public void enrollUser() {
        mEnrollUserUseCase.execute(new EnrollUserObserver(), ParseUser.getCurrentUser().getObjectId());
    }

    private void showEventDetailsInView(Event event) {
        EventModel eventModel = mEventDataMapper.transform(event);
        mEventDetailsView.renderEvent(eventModel);
    }

    private void showMenuInViewBasedOn(String authorId) {
        int menuId = authorId.equals(ParseUser.getCurrentUser().getObjectId()) ?
                R.menu.menu_event_details_organizer :
                R.menu.menu_event_details_participant;

        mEventDetailsView.inflateMenu(menuId);
    }

    private void showEnrollmentIndicator(final Event event) {
        event.getParticipantsRelation().getQuery()
                .whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId())
                .findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        boolean isParticipant = !objects.isEmpty();
                        boolean isOrganizer = ParseUser.getCurrentUser().getObjectId().equals(event.getAuthorId());
                        mEventDetailsView.setEnrollmentIndicatorActive(isOrganizer || isParticipant);
                    }
                });
    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mEventDetailsView.getContext(), error);
        mEventDetailsView.showError(errorMessage);
    }

    private void getEventDetails() {
        mGetEventDetailsUseCase.execute(new EventDetailsSubscriber());
    }

    private final class EventDetailsSubscriber extends DefaultSubscriber<Event> {
        @Override
        public void onCompleted() {
            mEventDetailsView.hideViewWaiting();
        }

        @Override
        public void onError(Throwable e) {
            mEventDetailsView.hideViewWaiting();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onNext(Event event) {
            showEventDetailsInView(event);
            showMenuInViewBasedOn(event.getAuthorId());
            showEnrollmentIndicator(event);
        }
    }

    private final class EnrollUserObserver extends NoEmittingObserver<Void> {
        @Override
        public void onCompleted() {
            mEventDetailsView.setEnrollmentIndicatorActive(true);
        }

        @Override
        public void onError(Throwable e) {
            showErrorMessage((Exception) e);
        }
    }
}
