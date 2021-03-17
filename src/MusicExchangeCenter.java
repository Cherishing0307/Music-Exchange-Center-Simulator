import javafx.util.Pair;
import sun.reflect.generics.tree.Tree;
import java.util.*;

public class MusicExchangeCenter {

    ArrayList<User> users;
    HashMap<String, Float> royalties;
    ArrayList<Song> downloadedSongs;

    public MusicExchangeCenter() {
        users = new ArrayList<>();
        downloadedSongs = new ArrayList<>();
    }

    public ArrayList<Song> getDownloadedSongs() {
        return downloadedSongs;
    }

    public ArrayList<User> onlineUsers() {
        ArrayList<User> onlineUsers = new ArrayList<>();
        for (User u : users) {
            if (u.isOnline()) {
                onlineUsers.add(u);
            }
        }
        return onlineUsers;
    }

    public ArrayList<Song> allAvailableSongs() {
        ArrayList<Song> allAvailableSongs = new ArrayList<>();
        for (User u : onlineUsers()) {
            for (Song s : u.getSongList()) {
                allAvailableSongs.add(s);
            }
        }
        return allAvailableSongs;
    }

    public String toString() {
        return "Music Exchange Center (" + onlineUsers().size() + " users online, " + allAvailableSongs().size() + " songs available)";
    }

    public User userWithName(String s) {
        User withName = null;
        for (User u : users) {
            if (u.getUserName() == s)
                withName = u;
        }
        return withName;
    }

    public void registerUser(User x) {
        if (!users.contains(userWithName(x.getUserName()))) {
            users.add(x);
        }
    }

    public ArrayList<Song> availableSongsByArtist(String artist) {
        ArrayList<Song> availableSongsByArtist = new ArrayList<>();
        for (User u : onlineUsers()) {
            for (Song s : u.getSongList()) {
                if (s.getArtist().contains(artist))
                    availableSongsByArtist.add(s);
            }
        }
        return availableSongsByArtist;
    }

    public Song getSong(String title, String ownerName) {
        Song givenSong = null;
        for (User u : this.onlineUsers()) {
            if (u.getUserName().equals(ownerName)) {
                    if (u.songWithTitle(title)!=null) {
                        givenSong = u.songWithTitle(title);
                        downloadedSongs.add(givenSong);

                    }
            }
        }
        return givenSong;
    }

    public void displayRoyalties() {
        royalties = new HashMap<>();
        float rate = 0.25f;
        for (Song s : downloadedSongs) {
            if (!royalties.containsKey(s.getArtist()))
                royalties.put(s.getArtist(), rate);
            else
                royalties.put(s.getArtist(), royalties.get(s.getArtist()) + rate);
        }
        System.out.println(String.format("%-10s","Amount") + "Artist");
        System.out.println("-------------------");
        for(Map.Entry<String,Float> h: royalties.entrySet()){
            System.out.println(String.format("%-10s", String.format("$%.2f",h.getValue())) + h.getKey());
        }
    }

    public TreeSet<Song> uniqueDownloads() {
        TreeSet<Song> unique = new TreeSet<>(downloadedSongs);
        return unique;
    }

    public ArrayList<Pair<Integer, Song>> songsByPopularity() {
        ArrayList<Pair<Integer,Song>> unique = new ArrayList<>();
        int downloads = 0;
        for(Song s: downloadedSongs){
            for(Song ss: downloadedSongs){
                if(s.getTitle().equals(ss.getTitle()))
                    downloads++;
            }
            Pair<Integer,Song> songPair = new Pair<>(downloads,s);
            unique.add(songPair);
            downloads = 0;
        }
        HashSet<Pair<Integer,Song>> songHashSet = new HashSet<>(unique);
        ArrayList<Pair<Integer,Song>> popular = new ArrayList<>(songHashSet);
        Collections.sort(popular, new Comparator<Pair<Integer, Song>>() {
            public int compare(Pair<Integer, Song> p1, Pair<Integer, Song> p2) {
                return p2.getKey()-p1.getKey();
            }
        });
        return popular;
    }
}