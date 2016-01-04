package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.domain.interactor.NoEmittingObserver;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.EventDetailsFragment;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EventDetailsView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class EventDetailsPresenterImpl implements EventDetailsPresenter {

    private String mEventId;
    private EventDetailsView mEventDetailsView;
    private UseCase mGetEventDetailsUseCase, mEnrollUserUseCase,
            mDisenrollUserUseCase, mCancelEventUseCase;
    private EventDataMapper mEventDataMapper;

    @Inject
    EventDetailsPresenterImpl(@Named("eventDetails") UseCase getEventDetailsUseCase,
                              @Named("enrollUser") UseCase enrollUserUseCase,
                              @Named("disenrollUser") UseCase disenrollUserUseCase,
                              @Named("cancelEvent") UseCase cancelEventUseCase,
                              EventDataMapper eventDataMapper) {
        mGetEventDetailsUseCase = getEventDetailsUseCase;
        mEnrollUserUseCase = enrollUserUseCase;
        mDisenrollUserUseCase = disenrollUserUseCase;
        mCancelEventUseCase = cancelEventUseCase;
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
        mEnrollUserUseCase.execute(new EnrollUserObserver(), com.parse.ParseUser.getCurrentUser());
    }

    @Override
    public void disenrollUser() {
        mDisenrollUserUseCase.execute(new DisenrollUserObserver(), com.parse.ParseUser.getCurrentUser());

    }

    @Override
    public void cancelEvent() {
        mEventDetailsView.showViewCanceling();
        mCancelEventUseCase.execute(new CancelEventObserver(), null);
    }

    private void showEventDetailsInView(Event event) {
        EventModel eventModel = mEventDataMapper.transform(event);
        mEventDetailsView.renderEvent(eventModel);
    }

    private void defineRoles(final Event event) {
        event.getParticipantsRelation().getQuery()
                .whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId())
                .findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        boolean isParticipant = !objects.isEmpty();
                        boolean isOrganizer = ParseUser.getCurrentUser().getObjectId().equals(event.getAuthorId());
                        showViewsBasedOnRoles(event, isParticipant, isOrganizer);

                    }
                });
    }

    private void showViewsBasedOnRoles(Event event, boolean isParticipant, boolean isOrganizer) {
        mEventDetailsView.setEnrollmentIndicatorsActive(isOrganizer || isParticipant);
        boolean isEventActive = event.getDateTimeInMillis() >= System.currentTimeMillis();
        if (!isOrganizer) {
            mEventDetailsView.inflateMenu(R.menu.menu_event_details_participant);
            mEventDetailsView.setOrganizerInfo(event.getAuthor().getUsername(), event.getAuthor().getEmail());
            mEventDetailsView.setParticipantMenuItemsEnabled(isParticipant && isEventActive);
        } else {
            mEventDetailsView.inflateMenu(R.menu.menu_event_details_organizer);
            mEventDetailsView.setOrganizerMenuItemsEnabled(isEventActive);
        }
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
            defineRoles(event);
        }
    }

    private final class EnrollUserObserver extends NoEmittingObserver<Void> {
        @Override
        public void onCompleted() {
            mEventDetailsView.setEnrollmentIndicatorsActive(true);
            mEventDetailsView.setParticipantMenuItemsEnabled(true);
        }

        @Override
        public void onError(Throwable e) {
            showErrorMessage((Exception) e);
        }
    }

    private final class DisenrollUserObserver extends NoEmittingObserver<Void> {
        @Override
        public void onCompleted() {
            mEventDetailsView.setEnrollmentIndicatorsActive(false);
            mEventDetailsView.setParticipantMenuItemsEnabled(false);
            //todo notify enrolled fragment
        }

        @Override
        public void onError(Throwable e) {
            showErrorMessage((Exception) e);
        }
    }

    private final class CancelEventObserver extends NoEmittingObserver<Void> {
        @Override
        public void onCompleted() {
            mEventDetailsView.hideViewCanceling();
        }

        @Override
        public void onError(Throwable e) {
            mEventDetailsView.hideViewCanceling();
            showErrorMessage((Exception) e);
        }
    }

    @Override
    public void onDestroy() {
        mGetEventDetailsUseCase.unsubscribe();
        mEnrollUserUseCase.unsubscribe();
        mDisenrollUserUseCase.unsubscribe();
        mCancelEventUseCase.unsubscribe();
    }
}