package org.example;

import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

@Data
@Builder
public class Composer implements Comparable<Composer>, Serializable {
    private String name;
    private String era;
    private int dateOfBirth;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Piece> pieces = new ArrayList<>();

//    @Override
//    public String toString(){
//        StringBuilder word =  new StringBuilder(this.name + " from " + this.era + " era." + "Born in " + this.dateOfBirth + ". Famous pieces: ");
//                for(Piece p : pieces){
//                    word.append(p.toString());
//                    word.append(" ; ");
//                };
//                return word.toString();
//    }

    @Override
    public int compareTo(Composer c){
        return this.name.compareTo(c.getName());
    }
}
