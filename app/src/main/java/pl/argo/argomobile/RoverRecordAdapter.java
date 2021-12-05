package pl.argo.argomobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RoverRecordAdapter extends ArrayAdapter<RoverRecord> {

    public RoverRecordAdapter(@NonNull Context context, int resource, @NonNull List<RoverRecord> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.rover_list_row, null);
        }

        ImageView roverImage = convertView.findViewById(R.id.roverImage);
        TextView roverName = convertView.findViewById(R.id.roverName);

        roverImage.setImageResource(getItem(position).getImageId());
        roverName.setText(getItem(position).getName());

        return convertView;
    }
}
