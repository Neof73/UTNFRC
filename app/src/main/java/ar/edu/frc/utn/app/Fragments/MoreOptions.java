package ar.edu.frc.utn.app.Fragments;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import java.util.ArrayList;
import java.util.List;
import ar.edu.frc.utn.app.Main2Activity;
import ar.edu.frc.utn.app.R;
import java.util.HashMap;
import java.util.Map;
/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MoreOptions {
    private Context context;

    private static final int COUNT = 5;

    public MoreOptions(Context context) {
        this.context = context;
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createMoreItem(context, i));
        }
    }

    public List<MoreItem> ITEMS = new ArrayList<MoreItem>();

    private void addItem(MoreItem item) {
        ITEMS.add(item);
    }

    private static MoreItem createMoreItem(Context context, int position) {
        switch (position) {
            case 1: return new MoreItem(context, R.string.label_tab2, Main2Activity.TAG_FRAGMENT_CURSOS,R.string.cursos_description, R.drawable.curso);
            case 2: return new MoreItem(context, R.string.label_tab4, Main2Activity.TAG_FRAGMENT_CONTACTO,R.string.contacto_description, R.drawable.contacto);
            case 3: return new MoreItem(context, R.string.label_tab5, Main2Activity.TAG_FRAGMENT_RADIO,R.string.radio_description, R.drawable.radio);
            case 4: return new MoreItem(context, R.string.label_tab6, Main2Activity.TAG_FRAGMENT_GOTOMEETING,R.string.g2m_description, R.drawable.g2m);
            case 5: return new MoreItem(context, R.string.label_tab9, Main2Activity.TAG_FRAGMENT_VIDEOCONF,R.string.videoconferencia_description, R.drawable.videoconf);
        }
        return null;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MoreItem
    {
        public final String ButtonCaption;
        public final String ItemDescription;
        public final long ItemId;
        public final String FragmentTag;

        public MoreItem(Context context, int buttonCaption, String fragmentTag, int itemDescription, long itemId) {
            this.ButtonCaption = context.getString(buttonCaption);
            this.ItemDescription = context.getString(itemDescription);
            this.FragmentTag = fragmentTag;
            this.ItemId = itemId;
        }
        @Override
        public String toString() {
            return ItemDescription;
        }
    }
}
