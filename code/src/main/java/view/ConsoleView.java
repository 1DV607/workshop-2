package view;

import java.util.ArrayList;
import java.util.List;

/**
 * Main view for console window.
 * Contains a list of Views and is responsible for showing view selected by the controller.
 */
public class ConsoleView {

    public List<View> viewList;

    public ConsoleView() {
        viewList = new ArrayList<View>();
    }

    public void showView(int viewSelection) {
        viewList.get(viewSelection).show();
    }

    public void addView(View view) {
        viewList.add(view);
    }
}
