import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Objects;


public class TicTac {

    static String[] b;
    static String t;
    static String Player1;
    static String Player2;
    static String SaveName = "";
    static ArrayList<String> Game = new ArrayList<>();

    public TicTac() {
    }

    public ArrayList<String> loader(String filename) throws IOException {
        ArrayList<String> step = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                step.add(line);
                System.out.println(line);
            }
            reader.close();
            System.out.println("--------");
            SaveName = filename;
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        return step;
    }


    public void Saver(ArrayList<String> step, String filename) throws IOException {
        int lenght = filename.length();
        String filetype = "";
        int dot = filename.indexOf(".");

        for (int i = dot; i < lenght; i++) {
            filetype += filename.charAt(i);
        }

        if (filetype.equals(".txt")) {
            FileWriter writer = new FileWriter(filename);
            for (String sp : step) {
                writer.write(sp + System.lineSeparator());
            }
            writer.close();
        } else {
            System.out.println("File type is incorrect!");
        }
    }


    public void Start() throws IOException {
        Scanner s = new Scanner(System.in);
        boolean k = true;
        while (k) {
            String str = s.nextLine();
            String[] input = str.split(" ");
            if ((input.length != 3) && (input.length != 2)) {
                System.out.println("invalid input");
            } else if (((Objects.equals(input[0], "/start")) && (input[1] != null) && (input[2] != null)) || ((Objects.equals(input[0], "/load")) && (input[1] != null))) {
                int rnd = 1;
                boolean GameStart = true;

                ArrayList<String> p1 = new ArrayList<>();
                ArrayList<String> p2 = new ArrayList<>();

                String SaveName = "";

                if (input[0].equals("/load")) {
                    Game = this.loader(input[1]);
                    if (Game.size() == 0) {
                        GameStart = false;
                    } else if (Objects.equals(Game.get(Game.size() - 1), "end")) {
                        System.out.println("The game is already over!");
                        GameStart = false;
                    } else {
                        SaveName = input[1];
                        SaveName += ".txt";
                        rnd = Game.size();

                        for (int i = 0; i < rnd; i++) {
                            if (i % 2 == 0) {
                                p1.add(Game.get(i));
                            } else {
                                p2.add(Game.get(i));
                            }
                        }
                    }
                } else {
                    Game = new ArrayList<>();
                    rnd = 0;
                    Player1 = input[1];
                    Player2 = input[2];
                    String time = DateTimeFormatter.ofPattern("yyyy-mm-dd hh-mm-ss").format(LocalDateTime.now());
                    time = time.replace(" ", "-");
                    SaveName += time + ".txt";
                    SaveName = SaveName;
                    Saver(Game, SaveName);
                }

                while (GameStart) {
                    String cr;
                    if (rnd % 2 == 0) {
                        cr = Player1;
                    } else {
                        cr = Player2;
                    }
                    System.out.println(cr + "'s turn");
                    String slot = s.nextLine();
                    String[] inp = slot.split(" ");

                    if ((Objects.equals(inp[0], "/put")) && (inp[1] != null) && (inp[1].length() == 1)) {
                        if ((!inp[1].equals("1")) && (!inp[1].equals("2")) && (!inp[1].equals("3")) && (!inp[1].equals("4")) && (!inp[1].equals("5")) &&
                                (!inp[1].equals("6")) && (!inp[1].equals("7")) && (!inp[1].equals("8")) && (!inp[1].equals("9"))) {
                            System.out.println("Wrong input! Try another number : ");
                        } else if (Game.contains(inp[1])) {
                            System.out.println("This slot is already taken! Try another number : ");
                        } else {
                            Game.add(inp[1]);
                            if (Game.size() % 2 == 0) {
                                p2.add(inp[1]);
                            } else {
                                p1.add(inp[1]);
                            }

                            if (((p1.contains("1")) && (p1.contains("2")) && (p1.contains("3"))) ||
                                    ((p1.contains("4")) && (p1.contains("5")) && (p1.contains("6"))) ||
                                    ((p1.contains("7")) && (p1.contains("8")) && (p1.contains("9"))) ||
                                    ((p1.contains("1")) && (p1.contains("4")) && (p1.contains("7"))) ||
                                    ((p1.contains("2")) && (p1.contains("5")) && (p1.contains("8"))) ||
                                    ((p1.contains("3")) && (p1.contains("6")) && (p1.contains("9"))) ||
                                    ((p1.contains("1")) && (p1.contains("5")) && (p1.contains("9"))) ||
                                    ((p1.contains("3")) && (p1.contains("5")) && (p1.contains("7")))) {
                                System.out.println(Player1 + " is winner!!");
                                System.out.println("Game is over!");
                                Game.add("end");
                                Saver(Game, SaveName);
                                GameStart = false;
                            } else if (((p2.contains("1")) && (p2.contains("2")) && (p2.contains("3"))) ||
                                    ((p2.contains("4")) && (p2.contains("5")) && (p2.contains("6"))) ||
                                    ((p2.contains("7")) && (p2.contains("8")) && (p2.contains("9"))) ||
                                    ((p2.contains("1")) && (p2.contains("4")) && (p2.contains("7"))) ||
                                    ((p2.contains("2")) && (p2.contains("5")) && (p2.contains("8"))) ||
                                    ((p2.contains("3")) && (p2.contains("6")) && (p2.contains("9"))) ||
                                    ((p2.contains("1")) && (p2.contains("5")) && (p2.contains("9"))) ||
                                    ((p2.contains("3")) && (p2.contains("5")) && (p2.contains("7")))) {
                                System.out.println(Player2 + "is winner!!");
                                Game.add("end");
                                Saver(Game, SaveName);
                                GameStart = false;
                            } else if (rnd==9){
                                System.out.println("Nobody Wins.");
                                GameStart = false;
                            } else {
                                rnd++;
                            }
                        }
                    } else if (Objects.equals(inp[0], "/save")) {
                        Saver(Game, SaveName);
                        System.out.println("Game Saved successfully " + SaveName);
                        GameStart = false;
                    } else if (Objects.equals(inp[0], "Exit")) {
                        System.out.println("Game is over!");
                        GameStart = false;
                    } else {
                        System.out.println("Wrong input!");
                    }
                }
            }
        }
    }
}
