package nl.endhoven.bart.geoguesswipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Local variables

    private List<GeoObject> mGeoObjects;
    private GeoObjectAdapter mAdapter;
    private RecyclerView mGeoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGeoObjects = new ArrayList<>();
        for (int i = 0; i < GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES.length; i++) {
            mGeoObjects.add(new GeoObject(GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES[i],
                    GeoObject.PRE_DEFINED_GEO_OBJECT_IMAGE_IDS[i], GeoObject.PRE_DEFINED_GEO_OBJECT_BOOLEAN_ISINEUROPE[i]));
        }

        mGeoRecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        mGeoRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GeoObjectAdapter(this, mGeoObjects);
        mGeoRecyclerView.setAdapter(mAdapter);


        ItemTouchHelper.SimpleCallback simpleItemTouchCallbackLeft =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        // Swipe Left is for Europe
                        int position = (viewHolder.getAdapterPosition());
                        GeoObject geoObject = mGeoObjects.get(position);
                        if (geoObject.ismGeoIsInEurope()){
                            Toast toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT); toast.show();
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Wrong...", Toast.LENGTH_SHORT); toast.show();
                        }
                        mGeoObjects.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                };
        ItemTouchHelper.SimpleCallback simpleItemTouchCallbackRight =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        // Swipe Left is for Europe
                        int position = (viewHolder.getAdapterPosition());
                        GeoObject geoObject = mGeoObjects.get(position);
                        if (!geoObject.ismGeoIsInEurope()){
                            Toast toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT); toast.show();
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(), "Wrong...", Toast.LENGTH_SHORT); toast.show();
                        }
                        mGeoObjects.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                };
        ItemTouchHelper itemTouchHelperLeft = new ItemTouchHelper(simpleItemTouchCallbackLeft);
        ItemTouchHelper itemTouchHelperRight = new ItemTouchHelper(simpleItemTouchCallbackRight);

        itemTouchHelperLeft.attachToRecyclerView(mGeoRecyclerView);
        itemTouchHelperRight.attachToRecyclerView(mGeoRecyclerView);

    }
}
