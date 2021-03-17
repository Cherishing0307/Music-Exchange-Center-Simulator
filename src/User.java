import java.util.ArrayList;

public class User {
    private String userName;
    private boolean online;
    ArrayList<Song> songList = new ArrayList<>();

    public User()  { this(""); }

    public User(String u)  {
        userName = u;
        online = false;
    }

    public String getUserName() { return userName; }
    public boolean isOnline() { return online; }
    public ArrayList<Song> getSongList() { return songList; }

    public String toString()  {
        String s = "" + userName + ": " + songList.size() + " songs (";
        if (!online) s += "not ";
        return s + "online)";
    }

    public void addSong(Song s){
        s.owner = this;
        songList.add(s);
    }

    public int totalSongTime(){
        int total = 0;
        for(Song s: songList){
            total += s.getDuration();
        }
        return total;
    }

    public void register(MusicExchangeCenter m){
        m.registerUser(this);
    }

    public void logon(MusicExchangeCenter m){
        if(m.userWithName(this.getUserName())!=null){
            this.online = true;
        }
    }

    public void logoff(MusicExchangeCenter m){
        if(m.userWithName(this.getUserName())!=null){
            this.online = false;
        }
    }

    public ArrayList<String> requestCompleteSonglist(MusicExchangeCenter m){
        ArrayList<String> completeSonglist = new ArrayList<>();
        completeSonglist.add("    " + String.format("%-30s","TITLE") + String.format("%-20s","ARTIST") + String.format("%-10s","TIME") + String.format("%-15s","OWNER") + "\n");
        int numbered = 1;
        if(isOnline()) {
            for (Song s : m.allAvailableSongs()) {
                completeSonglist.add(String.format("%2d",numbered) + ". " + String.format("%-30s", s.getTitle()) + String.format("%-20s", s.getArtist()) + String.format("%-10s",s.getMinutes() + ":" + String.format("%02d",s.getSeconds())) + String.format("%-15s", s.owner.getUserName()));
                numbered++;
            }
        }
        return completeSonglist;
    }

    public ArrayList<String> requestSonglistByArtist(MusicExchangeCenter m, String artist){
        ArrayList<String> songlistByArtist = new ArrayList<>();
        songlistByArtist.add("    " + String.format("%-30s","TITLE") + String.format("%-20s","ARTIST") + String.format("%-10s","TIME") + String.format("%-15s","OWNER") + "\n");
        int numbered = 1;
        if(isOnline()&&m.userWithName(this.getUserName())!=null){
            for (Song s: m.allAvailableSongs()){
                if(s.getArtist()==artist){
                    songlistByArtist.add(String.format("%2d",numbered) + ". " + String.format("%-30s", s.getTitle()) + String.format("%-20s", s.getArtist()) + String.format("%-10s",s.getMinutes() + ":" + String.format("%02d",s.getSeconds())) + String.format("%-15s", s.owner.getUserName()));
                    numbered++;
                }
            }
        }
        return songlistByArtist;
    }

    public Song songWithTitle(String s){
        Song songTitle = null;
        for(Song song: songList){
            if(song.getTitle()==s)
                songTitle = song;
        }
        return songTitle;
    }

    public void downloadSong(MusicExchangeCenter m, String title, String ownerName){
        Song downloadSong = m.getSong(title,ownerName);
        if(downloadSong!=null) {
            songList.add(downloadSong);
        }
    }

    // Various Users for test purposes
    public static User DiscoStew() {
        User  discoStew = new User("Disco Stew");
        discoStew.addSong(new Song("Hey Jude", "The Beatles", 4, 35));
        discoStew.addSong(new Song("Barbie Girl", "Aqua", 3, 54));
        discoStew.addSong(new Song("Only You Can Rock Me", "UFO", 4, 59));
        discoStew.addSong(new Song("Paper Soup Cats", "Jaw", 4, 18));
        return discoStew;
    }

    public static User SleepingSam() {
        User sleepingSam = new User("Sleeping Sam");
        sleepingSam.addSong(new Song("Meadows", "Sleepfest", 7, 15));
        sleepingSam.addSong(new Song("Calm is Good", "Waterfall", 6, 22));
        return sleepingSam;
    }

    public static User RonnieRocker() {
        User ronnieRocker = new User("Ronnie Rocker");
        ronnieRocker.addSong(new Song("Rock is Cool", "Yeah", 4, 17));
        ronnieRocker.addSong(new Song("My Girl is Mean to Me", "Can't Stand Up", 3, 29));
        ronnieRocker.addSong(new Song("Only You Can Rock Me", "UFO", 4, 52));
        ronnieRocker.addSong(new Song("We're Not Gonna Take It", "Twisted Sister", 3, 9));
        return ronnieRocker;
    }

    public static User CountryCandy() {
        User countryCandy = new User("Country Candy");
        countryCandy.addSong(new Song("If I Had a Hammer", "Long Road", 4, 15));
        countryCandy.addSong(new Song("My Man is a 4x4 Driver", "Ms. Lonely", 3, 7));
        countryCandy.addSong(new Song("This Song is for Johnny", "Lone Wolf", 4, 22));
        return countryCandy;
    }

    public static User PeterPunk() {
        User peterPunk = new User("Peter Punk");
        peterPunk.addSong(new Song("Bite My Arms Off", "Jaw", 4, 12));
        peterPunk.addSong(new Song("Where's My Sweater", "The Knitters", 3, 41));
        peterPunk.addSong(new Song("Is that My Toenail ?", "Clip", 4, 47));
        peterPunk.addSong(new Song("Anvil Headache", "Clip", 4, 34));
        peterPunk.addSong(new Song("My Hair is on Fire", "Jaw", 3, 55));
        return peterPunk;
    }
}