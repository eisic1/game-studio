package ba.etf.rma23.data

class GameData {
    // U ovu klasu sam dodao jednu varijablu i par metoda namijenjenih za omiljene filmove
    companion object{
        private var gameList = listOf<Game>(); /*= listOf(
            Game("PUBG", "Google play", "29.3.2018", 4.9, "pubg", "T", "Krafton Game Union", "PUBG Corporation", "action",
                "PUBG MOBILE's 5th Anniversary is here! Imagination knows no bounds!\n" +
                        "Use novel items to build a creative space in the new themed gameplay Imagiversary!\n" +
                        "The first custom game modes are available for a limited time in World of Wonder! Experience a different PUBG MOBILE with loads of creative maps and gameplay!",
                listOf(
                UserRating("eisic2", 123456, 4.0),
                UserReview("vokanovic", 234567, "--"),
                UserRating("iprazina", 345678, 4.3),
                UserReview("sbecirovic", 456789, "//"),
                UserRating("nuser", 654321, 4.1)
            )),
            Game("Call of Duty", "Google play", "29.3.2018", 4.7, "callduty", "M", "Activision", "Activision", "action",
                "The Military Wallpaper App for Android and iPhone is a unique and interactive way to display a variety" +
                        "of military-themed wallpapers 4K and HD. This app allows users to select from a wide selection of" +
                        "high-resolution images and customize their device's background with a personal touch. With a variety" +
                        "of categories, users can easily find the perfect wallpaper to fit their taste.",
                listOf(
                UserRating("eisic2", 123456, 4.0),
                UserReview("vokanovic", 234567, "--"),
                UserRating("iprazina", 345678, 4.3),
                UserReview("sbecirovic", 456789, "//"),
                UserRating("nuser", 654321, 4.1)
            )),
            Game("8 Ball Pool", "Google play", "29.3.2018", 4.5, "ballpool", "E", "Miniclip", "Krafton Game Union", "sport",
                "It’s time for a new 8 Ball Pool update!\n" +
                        "A new great Season is down the road, so download the latest version and enjoy the game at maximum.\n" +
                        "Also, we’ve made some tweaks and improvements and solved some pesky bugs, making 8 Ball Pool even smoother for your entertainment!",
                listOf(
                UserRating("eisic2", 123456, 5.0),
                UserReview("vokanovic", 234567, "--")
            )),
            Game("Need for Speed", "Google play", "29.3.2018", 4.3, "needforspeed", "T", "Electronic Arts", "Activision", "drive",
                "Get the latest Need For Speed HD Wallpapers free here!\n" +
                        "Need For Speed HD Wallpaper is an application that provides images for Need For Speed lovers.\n" +
                        "Need For Speed HD wallpaper apps have many interesting collections that you can use as wallpaper.\n" +
                        "For those of you who like the Need For SpeedWallpapers HD wallpaper you must have this app.",
                listOf(
                UserRating("iprazina", 345678, 3.8)
            )),
            Game("Fifa", "Google play", "29.3.2018", 4.4, "fifa", "E", "Electronic Arts", "Miniclip", "sport",
                "FIFA Interpreting is a free app which allows users to listen to the interpretation of the speaker into another language from their smartphone.\n" +
                        "Event login code will be provided by the event organiser.",
                listOf(
                UserReview("sbecirovic", 456789, "//"),
                UserRating("nuser", 654321, 4.1)
            )),
            Game("World of Tank", "Google play", "29.3.2018", 4.2, "tanks", "T", "Wargaming", "Dark Horse Comics", "war",
                "Live Wallpaper created specially for World of Tanks Live Wallpaper contest.\n" +
                        "It contains 3 scenes that follow each other.\n" +
                        "Just watch the video, it is unlikely I can describe it better.\n" +
                        "Wallpaper have parallax effect. Designed for smartphones and tablets. Exellent works in portrait and landscape mode.",
                listOf(
                UserRating("iprazina", 345678, 4.3),
                UserReview("sbecirovic", 456789, "//")
            )),
            Game("Top Eleven", "Google play", "29.3.2018", 3.9, "topeleven", "E", "Nordeus", "Nordeus", "sport",
                "The world soccer stage awaits – it’s time to put yourself and your soccer tactics on the map in Top Eleven!\n" +
                        "Top Eleven is an entire soccer competition and universe right in your pocket! Top Eleven, the award-winning mobile" +
                        "soccer manager game, puts you in charge of your very own soccer club! From signing a team of superstar soccer players" +
                        "to creating your own stadium, in Top Eleven, it’s your club - and your rules!",
                listOf(
                UserRating("eisic2", 123456, 4.0)
            )),
            Game("Head Ball", "Google play", "29.3.2018", 4.0, "headball", "E", "Miniclip", "Masomo Gaming", "sport",
                "The Head Ball 2 Wallpaper HD app will continue to be refined. Make your cellphone unique by using Head Ball 2 wallpapers.\n" +
                        "Head Ball 2 are liked by many people so they are very famous, Don't hesitate to use this Head Ball 2 Wallpaper application.",
                listOf(
                UserReview("vokanovic", 234567, "--")
            )),
            Game("World War Heroes", "Google play", "29.3.2018", 4.1, "worldwar", "T", "Ukrainian", "Cyprus", "war",
                "Looking for free WW2 first person shooter games around? Battle players from around the world in a crazy WWII-era online first person shooter game!\n" +
                        "Destroy the enemy and survive tank battles in the center of Berlin and other cities. Participate in team and deathmatch battles, plant bombs and defend" +
                        "them, take part in world war massive warfare!",
                listOf(
                UserRating("iprazina", 345678, 4.3)
            )),
            Game("Bus Simulator", "Google play", "29.3.2018", 3.8, "bussimulator", "E", "Icebytes", "Astragon", "drive",
                "In our App Skin Bus Simulator Indonesia HD Wallpaper app Themes you will find the most popular, beautiful" +
                        "and famous pictures and photos! Any picture you like you can set as a desktop Wallpaper for Android Tablets" +
                        "or Android phones!\n" +
                        "If you like our free app, please don't forget to rate and write a review!",
                listOf(
                UserReview("sbecirovic", 456789, "//")
            )),
        )*/
        private var listFavouritesGames = arrayListOf<Game>()

        fun getAll(): List<Game> {
            return gameList
        }

        fun setGames(list: List<Game>){
            gameList = list;
        }
        // Metoda koja vraća sve omiljene filmove koji se nalaze u listi
        fun getFavouritesGames(): List<Game>{
            return listFavouritesGames
        }

        fun setFavouritesGames(list: List<Game>){
            listFavouritesGames = list as ArrayList<Game>
        }

        // Metoda koja sprema prosljeđenu igru u omiljene
        fun addFavouritesGames(game: Game){
            listFavouritesGames.add(game)
        }

        fun removeFavouritesGames(id: Int): Boolean{
            if(listFavouritesGames.size > 0){
                listFavouritesGames.removeIf { it.id == id }
                return true
            }
            return false
        }


        fun getDetails(title:String): Game? {
            /*var gameByTitle: Game? = null

            for (g in gameList){
                if(g.title === title) gameByTitle = g
            }

            return gameByTitle*/
            val getGameByTitle: ArrayList<Game> = arrayListOf()
            getGameByTitle.addAll(getAll())
            val gameFound = getGameByTitle.find { game -> title == game.title }
            return gameFound
        }
    }
}