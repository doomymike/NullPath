public class ServerTest {

    public static void main(String[] args) {

        Resources resources = new Resources();

        GameClient client = new GameClient();
        client.go();
        Thread t = new Thread(client);
        t.start();

        client.setUsername("user 1");

        while (true) {
            //if (client.isNewUserSet()) {
                resources.addPlayer(client.getUsers().get(client.getUsers().size()-1));
                //client.setNewUserSet(false);
                System.out.println("--");
                System.out.println(resources.getPlayers().get(resources.getPlayers().size()-1).getName() + " has been added");
            //}
        }

    }

}
