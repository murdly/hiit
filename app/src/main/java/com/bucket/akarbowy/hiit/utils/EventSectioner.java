package com.bucket.akarbowy.hiit.utils;

import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.adapters.SectionedRecyclerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akarbowy on 12.12.2015.
 */
public class EventSectioner {
    static SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

    public static List<SectionedRecyclerAdapter.Section> section(List<EventModel> events) {
        List<SectionedRecyclerAdapter.Section> sections = new ArrayList<>();

            int segmentPos = 0;
            do {
                sections.add(new SectionedRecyclerAdapter.Section(segmentPos, events.get(segmentPos).getDateAsString()));
                segmentPos = getUniqueSegment(events, segmentPos);
            } while (segmentPos != -1 && segmentPos <= events.size());

        return sections;
    }

    private static int getUniqueSegment(List<EventModel> events, int segmentPos) {
        Date current = events.get(segmentPos).getDate();
        for (int i = segmentPos + 1 ; i < events.size(); i++) {
            if(!isSameDay(events.get(i).getDate(), current))
                return i;
        }

        return -1;
    }

    private static boolean isSameDay(Date d1, Date curr) {
        return fmt.format(d1).equals(fmt.format(curr));
    }
}
