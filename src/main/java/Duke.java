import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String greeting = String.format("\n%s\n%s\n", "Hello! I'm Duke", "What can I do for you?");
        System.out.println(logo + greeting);

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        int numTask = 0;
        ArrayList<Task> taskList = new ArrayList<>();

        while(!input.equals("bye")) {
            if (input.matches("done\\s+\\d+")) {
                System.out.println("Nice! I've marked this task as done:");
                Task doneTask = taskList.get(Integer.valueOf(input.replaceAll("done\\s+", "")) - 1);
                doneTask.markAsDone();
                System.out.println(doneTask + "\n");
            } else if (input.matches("delete\\s+\\d+")) {
                System.out.println("Noted. I've removed this task:");
                int listRank = Integer.valueOf(input.replaceAll("delete\\s+", "")) - 1;
                Task deletedTask = taskList.get(listRank);
                System.out.println(deletedTask);
                taskList.remove(listRank);
                numTask--;
                System.out.println(String.format("Now you have %d tasks in the list.\n", numTask));
            } else if (!input.equals("list")) {

                if (input.matches("todo\\s+.+")) {
                    taskList.add(new Todo(input.replaceAll("todo\\s+", "")));
                } else if (input.matches("deadline\\s+.+")) {
                    String taskDetails = input.replaceAll("deadline\\s+", "");
                    String[] details = taskDetails.split("\\s+/by\\s+");
                    taskList.add(new Deadline(details[0], dateConversion(details[1])));
                } else if (input.matches("event\\s+.+")) {
                    String taskDetails = input.replaceAll("event\\s+", "");
                    String[] details = taskDetails.split("\\s+/at\\s+");
                    taskList.add(new Event(details[0], dateConversion(details[1])));
                } else {
                    try {
                        if (input.equals("todo") || input.equals("deadline") || input.equals("event")
                                || input.equals("done")) {
                            String message = String.format("The description of a %s cannot be empty.", input);
                            throw new InsufficientArgumentException(message);
                        } else {
                            throw new InvalidArgumentException("I'm sorry, but I don't know what that means :-(");
                        }
                    } catch (DukeException exception) {

                    }
                }

                if (taskList.size() == numTask + 1) {
                    System.out.println("Got it. I've added this task:");
                    System.out.println(taskList.get(numTask));
                    numTask++;
                    System.out.println(String.format("Now you have %d tasks in the list.\n", numTask));
                }
            } else {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < numTask; i++) {
                    System.out.println(String.format("%d.%s", i + 1, taskList.get(i)));
                }
                System.out.println();
            }

            if (sc.hasNext()) {
                input = sc.nextLine();
            } else {
                break;
            }
        }

        if (input.equals("bye")) {
            System.out.println("Bye. Hope to see you again soon!");
        }
    }
    public static String dateConversion(String s) {
        SimpleDateFormat numDateTime = new SimpleDateFormat("d/M/y HHmm");
        SimpleDateFormat textDateTime = new SimpleDateFormat("d MMMM y, h.mm a");
        try {
            if (s.matches("\\d+/\\d+/\\d+\\s+\\d+")) {
                Date d = numDateTime.parse(s);
                s = textDateTime.format(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
}