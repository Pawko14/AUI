package org.example;

import lombok.*;
import java.io.Serializable;

@Data
@Builder
public class Piece implements Comparable<Piece>, Serializable {
    private String pieceName;
    private int date;
    private String instrument;

    //    @ToString.Exclude
    @EqualsAndHashCode.Exclude
//    @Builder.Default
    private Composer composer;

    @Override
    public int compareTo(Piece p){
        return this.pieceName.compareTo(p.getPieceName());
    }

    @Override
    public String toString(){
        return this.pieceName + " made in " + this.date + " to be played on a " + this.instrument;
    }
}
