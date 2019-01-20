public class ServerTest2 {

    public static void main(String[] args) {

        GameClient client = new GameClient();
        client.go();
        client.setUsername("user 2");

        Resources resources = new Resources();

        while (true) {
            //if (client.isNewUserSet()) {
                resources.addPlayer(client.getUsers().get(client.getUsers().size()-1));
                //client.setNewUserSet(false);
                System.out.println(resources.getPlayers().get(resources.getPlayers().size()-1) + " has been added");
            //}
        }

    }

}
