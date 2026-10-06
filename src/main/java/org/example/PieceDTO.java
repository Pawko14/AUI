package org.example;

import lombok.*;
import java.io.Serializable;

@Data
@Builder
public class PieceDTO implements Comparable<PieceDTO>, Serializable {
    private String pieceName;
    private int date;
    private String instrument;

    //    @ToString.Exclude
    //    @Builder.Default
    private String composerName;

    @Override
    public int compareTo(PieceDTO p){
        return this.pieceName.compareTo(p.getPieceName());
    }

    @Override
    public String toString(){
        return this.pieceName + " made in " + this.date + " to be played on a " + this.instrument;
    }
}
