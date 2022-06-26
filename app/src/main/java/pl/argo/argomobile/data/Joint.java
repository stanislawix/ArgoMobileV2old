package pl.argo.argomobile.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Joint {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "joint_name")
    private String jointName;

    @ColumnInfo(name = "rover_id")
    private int roverId;
}
