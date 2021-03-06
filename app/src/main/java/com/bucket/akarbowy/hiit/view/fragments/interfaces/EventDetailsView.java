package com.bucket.akarbowy.hiit.view.fragments.interfaces;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.EventModel;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface EventDetailsView extends BaseView {

    void showViewWaiting();

    void hideViewWaiting();

    void showViewCanceling();

    void hideViewCanceling();

    void renderEvent(EventModel eventModel);

    void inflateMenu(int menuId);

    void setEnrollmentIndicatorsActive(boolean enrolled);

    void setOrganizerInfo(String username, String email);

    void setOrganizerMenuItemsEnabled(boolean isOrganizer);

    void setParticipantMenuItemsEnabled(boolean isParticipant);

}
