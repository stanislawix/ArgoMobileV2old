package pl.argo.argomobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pl.argo.argomobile.data.dto.RoverDto;

import java.util.List;

public class RoverRecordAdapter extends ArrayAdapter<RoverDto> {

    public RoverRecordAdapter(@NonNull Context context, int resource, @NonNull List<RoverDto> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.rover_list_row, null);
        }

        ImageView roverImage = convertView.findViewById(R.id.roverImage);
        TextView roverName = convertView.findViewById(R.id.roverName);
        TextView roverTopicPrefix = convertView.findViewById(R.id.roverTopicPrefix);

        //roverImage.setImageResource(getItem(position).getImageId());
        roverImage.setImageResource(R.drawable.turtle);
        roverName.setText(getItem(position).getName());
        roverTopicPrefix.setText("topicPrefix = /" + getItem(position).getTopicPrefix());

        return convertView;
    }
}
