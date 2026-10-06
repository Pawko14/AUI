package org.example;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static void main(String[] args) {

        // 2
        Composer chopin = Composer.builder().name("Chopin").era("Romantic").dateOfBirth(1810).build();
        Composer mozart = Composer.builder().name("Mozart").era("Classic").dateOfBirth(1756).build();
        Composer bach = Composer.builder().name("Bach").era("Baroque").dateOfBirth(1685).build();
        Composer liszt = Composer.builder().name("Liszt").era("Romantic").dateOfBirth(1811).build();

        Piece c1 = Piece.builder().pieceName("Winter Wind Etude op 25 no 11").date(1837).instrument("piano").composer(chopin).build();
        Piece c2 = Piece.builder().pieceName("Ballade no 1").date(1835).instrument("piano").composer(chopin).build();


        Piece m1 = Piece.builder().pieceName("Rondo alla Turca").date(1783 ).instrument("piano").composer(mozart).build();
        Piece m2 = Piece.builder().pieceName("Eine Kleine Nachtmusik").date(1787 ).instrument("violin").composer(mozart).build();

        Piece b1 = Piece.builder().pieceName("Sonata no 1 BWV 1001").date(1720).instrument("violin").composer(bach).build();
        Piece b2 = Piece.builder().pieceName("Prelude and Fugue in c minor BWV 847").date(1722).instrument("piano").composer(bach).build();

        Piece l1 = Piece.builder().pieceName("La Campanella").date(1835).instrument("piano").composer(liszt).build();

        chopin.getPieces().addAll(List.of(c1,c2));
        mozart.getPieces().addAll(List.of(m1,m2));
        bach.getPieces().addAll(List.of(b1,b2));
        liszt.getPieces().add(l1);

        // Lista do 6 !!
        List<Composer> composers = List.of(chopin,mozart,bach,liszt);

        System.out.println("Wypisanie lambda: ");
        composers.forEach(composer -> {
            System.out.println(composer);
            composer.getPieces().forEach(piece -> System.out.println(" " + piece));
        });

        // 3

        Set<Piece> allPieces = composers
                .stream()
                .flatMap(composer -> composer
                        .getPieces()
                        .stream())
                .collect(Collectors.toSet());

        System.out.println("Wypisanie Streamem: ");
        allPieces
                .stream()
                .forEach(System.out::println);


        // 4
        // to pierwsze i sortowanie po dacie i utwory tylko na pianino
        System.out.println("Zadanie 4: ");
        allPieces
                .stream()
                .filter(ins -> ins.getInstrument().equals("piano"))
                .sorted(Comparator.comparing(Piece::getDate))
                .forEach(System.out::println);

        // drugie posortowanie po nazwie i data > 1800
        System.out.println("Zadanie 4a: ");
        allPieces
                .stream()
                .filter(date -> date.getDate() > 1800)
                .sorted(Comparator.comparing(Piece::getPieceName))
                .forEach(System.out::println);

        // 5
        System.out.println("Zadanie 5");
        List<PieceDTO> listPiece = allPieces.stream()
                .map(p -> PieceDTO
                        .builder()
                        .pieceName(p.getPieceName())
                        .date(p.getDate())
                        .instrument(p.getInstrument())
                        .composerName(p.getComposer().getName())
                        .build())
                .sorted()
                .toList(); // tu mozna collect(Collectors.toList()) ale toList jest szybsze to zamienilem

        listPiece.stream().forEach(System.out::println);

        // 6
        System.out.println( " Zadanie 6 ");
        try {
            FileOutputStream fout = new FileOutputStream("plik.txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(composers);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try{
            FileInputStream fin = new FileInputStream("plik.txt");
            ObjectInputStream in = new ObjectInputStream(fin);

            List<Composer> allPiecesTaken = (List<Composer>) in.readObject();

            allPiecesTaken.stream().forEach(composer -> {
                    System.out.println(composer + " ");
                    composer
                            .getPieces()
                            .forEach(piece -> System.out.println("  " + piece));
                    });

        }
        catch(Exception e){
            e.printStackTrace();
        }

        // 7
        System.out.println( "Zadanie 7 ");

            ForkJoinPool fJP = new ForkJoinPool(2);
            try {
                fJP
                        .submit(() -> allPieces.
                                parallelStream().
                                forEach(piece -> System.out.println(Thread.currentThread().getName() + " , " + piece)))
                        .get();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally{
                fJP.shutdown();
            }

        // 8 - mapping
        //sprobuje zrobic mappowanie po instrumentach
        System.out.println( " --- 8 --- ");
        Map<String, List<Piece>> piecesByIns = allPieces.stream().collect(Collectors.groupingBy(Piece::getInstrument));

        piecesByIns.forEach((instrument,pieces) ->System.out.println(instrument + " - " + pieces));

    }
}

