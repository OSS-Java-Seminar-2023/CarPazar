let currentDiv = 0;
let allFormDivs = document.querySelectorAll("form > div");
let backButton = document.getElementById("back-button");
let nextButton = document.getElementById("next-button");

let modelDict = {

	      "Abarth": [
            "500",
            "Grande Punto"
          ],

          "Alfa Romeo": [
            "33",
            "33 SW",
            "75",
            "90",
            "145",
            "146",
            "147",
            "155",
            "156",
            "156 SW",
            "159",
            "159 SW",
            "164",
            "166",
            "8C Competizione",
            "Alfasud",
            "Alfetta",
            "Brera",
            "Cross Wagon",
            "Giulia",
            "Giulietta",
            "GT",
            "GTV",
            "GTV-6",
            "Milano",
            "MiTo",
            "RZ/SZ",
            "Spider",
            "Stelvio",
            "Tonate"
          ],

          "Aston Martin": [
            "DB7",
            "DB9",
            "DBS",
            "Lagonda",
            "Rapide",
            "V12 Vantage",
            "V8 Vantage",
            "Vanquish",
            "Vantage",
            "Virage",
            "Volante"
          ],

          "Audi": [
            "50",
            "60",
            "80",
            "80 Avant",
            "90",
            "100",
            "100 Avant",
            "200",
            "A1",
            "A2",
            "A3",
            "A3 Cabrio",
            "A3 Coupe",
            "A4",
            "A4 Cabrio",
            "A4 Allroad",
            "A5",
            "A5 Sportback",
            "A5 Coupe",
            "A5 Cabrio",
            "A6",
            "A6 Allroad",
            "A6 Avant",
            "A7",
            "A8",
            "e-tron",
            "Q2",
            "Q3",
            "Q4 e-tron",
            "Q5",
            "Q7",
            "Quattro",
            "R8",
            "RS 4",
            "RS 5",
            "RS 6",
            "S4 Avant",
            "S5",
            "S6 Avant",
            "S7",
            "S8",
            "TT",
            "TT RS",
            "TTS",
            "V8 Quattro"
          ],

          "Bentley": [
            "Arnage",
            "Azure",
            "Brooklands",
            "Continental",
            "Corniche",
            "Eight",
            "Mulsanne",
            "Turbo R"
          ],

          "BMW": [
            "128i",
            "135i",
            "135is",
            "318i",
            "318iC",
            "318iS",
            "318ti",
            "320i",
            "323ci",
            "323i",
            "323is",
            "323iT",
            "325Ci",
            "325e",
            "325es",
            "325i",
            "325is",
            "325iX",
            "325xi",
            "328Ci",
            "328i",
            "328iS",
            "328xi",
            "330Ci",
            "330i",
            "330xi",
            "335d",
            "335i",
            "335is",
            "335xi",
            "ActiveHybrid 3",
            "325",
            "524td",
            "525i",
            "525xi",
            "528e",
            "528i",
            "528iT",
            "528xi",
            "530i",
            "530iT",
            "530xi",
            "533i",
            "535i",
            "535i Gran Turismo",
            "535xi",
            "540i",
            "545i",
            "550i",
            "550i Gran Turismo",
            "ActiveHybrid 5",
            "633CSi",
            "635CSi",
            "640i",
            "640i Gran Coupe",
            "645Ci",
            "650i",
            "650i Gran Coupe",
            "L6",
            "733i",
            "735i",
            "735iL",
            "740i",
            "740iL",
            "740Li",
            "745i",
            "745Li",
            "750i",
            "750iL",
            "750Li",
            "760i",
            "760Li",
            "ActiveHybrid 7",
            "Alpina B7",
            "840Ci",
            "850Ci",
            "850CSi",
            "850i",
            "L7",
            "1 Series M",
            "M Coupe",
            "M Roadster",
            "M2",
            "M3",
            "M4",
            "M5",
            "M6",
            "X5 M",
            "X6 M",
            "ActiveHybrid X6",
            "X1",
            "X2",
            "X3",
            "X4",
            "X5",
            "X6",
            "X7",
            "XM",
            "Z1",
            "Z3",
            "Z4",
            "Z8"
          ],

          "Bugatti": [
            "Chiron",
            "Veyron"
          ],

          "Cadillac": [
            "Allante",
            "ATS",
            "Brougham",
            "Catera",
            "Cimarron",
            "CTS",
            "De Ville",
            "DTS",
            "Eldorado",
            "Escalade",
            "Escalade ESV",
            "Escalade EXT",
            "Fleetwood",
            "Seville",
            "SRX",
            "STS",
            "XLR",
            "XTS"
          ],

          "Chevrolet": [
            "Astro",
            "Avalanche",
            "Aveo",
            "Aveo5",
            "Beretta",
            "Blazer",
            "Camaro",
            "Caprice",
            "Captiva Sport",
            "Cavalier",
            "Celebrity",
            "Chevette",
            "Citation",
            "Cobalt",
            "Colorado",
            "Corsica",
            "Corvette",
            "Cruze",
            "El Camino",
            "Equinox",
            "Express Van",
            "G Van",
            "HHR",
            "Impala",
            "Kodiak C4500",
            "Lumina",
            "Lumina APV",
            "LUV",
            "Malibu",
            "Metro",
            "Monte Carlo",
            "Nova",
            "Prizm",
            "S10 Blazer",
            "S10 Pickup",
            "Silverado and other C/K1500",
            "Silverado and other C/K2500",
            "Silverado and other C/K3500",
            "Sonic",
            "Spark",
            "Spectrum",
            "Sprint",
            "SSR",
            "Suburban",
            "Tahoe",
            "Tracker",
            "TrailBlazer",
            "TrailBlazer EXT",
            "Traverse",
            "Uplander",
            "Venture",
            "Volt"
          ],

          "Chrysler": [
            "200",
            "300",
            "300M",
            "Aspen",
            "Caravan",
            "Cirrus",
            "Concorde",
            "Conquest",
            "Cordoba",
            "Crossfire",
            "E Class",
            "Fifth Avenue",
            "Grand Voyager",
            "Imperial",
            "Intrepid",
            "Laser",
            "LeBaron",
            "LHS",
            "Neon",
            "New Yorker",
            "Newport",
            "Pacifica",
            "Prowler",
            "PT Cruiser",
            "Sebring",
            "TC by Maserati",
            "Town &amp; Country",
            "Voyager"
          ],

          "Citroen": [
              "2CV",
              "AX",
              "Berlingo",
              "C-Elysee",
              "C-Zero",
              "C1",
              "C2",
              "C3",
              "C4",
              "C5",
              "C6",
              "C8",
              "Jumper",
              "Jumpy",
              "Xsara"
          ],

          "Dacia": [
            "Dokker",
            "Duster",
            "Jogger",
            "Lodgy",
            "Logan",
            "Sandero",
          ],

          "Daewoo": [
            "Lanos",
            "Leganza",
            "Nubira"
          ],

          "Dodge": [
            "400",
            "600",
            "Aries",
            "Avenger",
            "Caliber",
            "Caravan",
            "Challenger",
            "Charger",
            "Colt",
            "Conquest",
            "D/W Truck",
            "Dakota",
            "Dart",
            "Daytona",
            "Diplomat",
            "Durango",
            "Dynasty",
            "Grand Caravan",
            "Intrepid",
            "Journey",
            "Lancer",
            "Magnum",
            "Mirada",
            "Monaco",
            "Neon",
            "Nitro",
            "Omni",
            "Raider",
            "Ram 1500 Truck",
            "Ram 2500 Truck",
            "Ram 3500 Truck",
            "Ram 4500 Truck",
            "Ram 50 Truck",
            "RAM C/V",
            "Ram SRT-10",
            "Ram Van",
            "Ram Wagon",
            "Ramcharger",
            "Rampage",
            "Shadow",
            "Spirit",
            "Sprinter",
            "SRT-4",
            "St. Regis",
            "Stealth",
            "Stratus",
            "Viper"
          ],

          "Ferrari": [
            "308 GTB Quattrovalvole",
            "308 GTBI",
            "308 GTS Quattrovalvole",
            "308 GTSI",
            "328 GTB",
            "328 GTS",
            "348 GTB",
            "348 GTS",
            "348 Spider",
            "348 TB",
            "348 TS",
            "360",
            "456 GT",
            "456M GT",
            "458 Italia",
            "512 BBi",
            "512M",
            "512TR",
            "550 Maranello",
            "575M Maranello",
            "599 GTB Fiorano",
            "599 GTO",
            "612 Scaglietti",
            "California",
            "Enzo",
            "F355",
            "F40",
            "F430",
            "F50",
            "FF",
            "Mondial",
            "Testarossa"
          ],

          "Fiat": [
            "124",
            "125",
            "126",
            "127",
            "130",
            "131",
            "132",
            "500",
            "500L",
            "500X",
            "2000 Spider",
            "Bertone X1/9",
            "Brava",
            "Bravo",
            "Cinquecento",
            "Panda",
            "Pininfarina Spider",
            "Punto",
            "Strada",
            "Tipo",
            "Ulysee",
            "X1/9"
          ],

          "Ford": [
            "Aerostar",
            "Aspire",
            "Bronco",
            "Bronco II",
            "C-MAX",
            "Club Wagon",
            "Contour",
            "Courier",
            "Crown Victoria",
            "E-150 and Econoline 150",
            "E-250 and Econoline 250",
            "E-350 and Econoline 350",
            "Edge",
            "Escape",
            "Escort",
            "Excursion",
            "EXP",
            "Expedition",
            "Expedition EL",
            "Explorer",
            "Explorer Sport Trac",
            "F100",
            "F150",
            "F250",
            "F350",
            "F450",
            "Fairmont",
            "Festiva",
            "Fiesta",
            "Five Hundred",
            "Flex",
            "Focus",
            "Freestar",
            "Freestyle",
            "Fusion",
            "Granada",
            "GT",
            "Kuga",
            "LTD",
            "Mustang",
            "Probe",
            "Ranger",
            "Taurus",
            "Taurus X",
            "Tempo",
            "Thunderbird",
            "Transit Connect",
            "Windstar",
            "ZX2 Escort"
          ],

          "Honda": [
            "Accord",
            "Civic",
            "CR-V",
            "CR-Z",
            "CRX",
            "Accord Crosstour",
            "Crosstour",
            "Del Sol",
            "Element",
            "Fit",
            "Insight",
            "Odyssey",
            "Passport",
            "Pilot",
            "Prelude",
            "Ridgeline",
            "S2000"
          ],

          "Hummer": [
            "H1",
            "H2",
            "H3",
            "H3T"
          ],

          "Hyundai": [
            "Accent",
            "Azera",
            "Elantra",
            "Elantra Coupe",
            "Elantra Touring",
            "Entourage",
            "Equus",
            "Excel",
            "Genesis",
            "Genesis Coupe",
            "i10",
            "i20",
            "i30",
            "i40",
            "Santa Fe",
            "Scoupe",
            "Sonata",
            "Tiburon",
            "Tucson",
            "Veloster",
            "Veracruz",
            "XG300",
            "XG350"
          ],

          "Infiniti": [
            "EX35",
            "EX37",
            "FX35",
            "FX37",
            "FX45",
            "FX50",
            "G20",
            "G25",
            "G35",
            "G37",
            "I30",
            "I35",
            "J30",
            "JX35",
            "M30",
            "M35",
            "M35h",
            "M37",
            "M45",
            "M56",
            "Q45",
            "QX4",
            "QX56"
          ],

          "Iveco": [
            "Massif"
          ],

          "Jaguar": [
            "Daimler",
            "S-Type",
            "X-Type",
            "XF",
            "XJ12",
            "XJ6",
            "XJR",
            "XJR-S",
            "XJS",
            "XJ Vanden Plas",
            "XJ",
            "XJ8",
            "XJ8 L",
            "XJ Sport",
            "XK8",
            "XK",
            "XKR"
          ],

          "Jeep": [
            "Cherokee",
            "CJ",
            "Comanche",
            "Commander",
            "Compass",
            "Grand Cherokee",
            "Grand Wagoneer",
            "Liberty",
            "Patriot",
            "Pickup",
            "Scrambler",
            "Wagoneer",
            "Wrangler"
          ],

          "Kia": [
            "Amanti",
            "Besta",
            "Borrego",
            "Ceed",
            "Forte",
            "Forte Koup",
            "Optima",
            "Rio",
            "Rio5",
            "Rondo",
            "Sedona",
            "Sephia",
            "Sorento",
            "Soul",
            "Spectra",
            "Spectra5",
            "Sportage",
            "XCeed"
          ],

          "Lada": [
            "110",
            "111",
            "112",
            "1300",
            "Aleko",
            "Desetka",
            "Niva",
            "Nova",
            "Samara"
          ],

          "Lamborghini": [
            "Aventador",
            "Countach",
            "Diablo",
            "Gallardo",
            "Huracan",
            "Jalpa",
            "LM002",
            "Murcielago",
            "Urus"
          ],

          "Lancia": [
            "Beta",
            "Lybra",
            "Musa",
            "Zagato"
          ],

          "Land Rover": [
            "Defender",
            "Discovery",
            "Freelander",
            "LR2",
            "LR3",
            "LR4",
            "Range Rover",
            "Range Rover Evoque",
            "Range Rover Sport"
          ],

          "Lexus": [
            "CT 200h",
            "ES 250",
            "ES 300",
            "ES 300h",
            "ES 330",
            "ES 350",
            "GS 300",
            "GS 350",
            "GS 400",
            "GS 430",
            "GS 450h",
            "GS 460",
            "GX 460",
            "GX 470",
            "HS 250h",
            "IS 250",
            "IS 250C",
            "IS 300",
            "IS 350",
            "IS 350C",
            "IS F",
            "LFA",
            "LS 400",
            "LS 430",
            "LS 460",
            "LS 600h",
            "LX 450",
            "LX 470",
            "LX 570",
            "RX 300",
            "RX 330",
            "RX 350",
            "RX 400h",
            "RX 450h",
            "SC 300",
            "SC 400",
            "SC 430"
          ],

          "Lotus": [
            "Elan",
            "Elise",
            "Esprit",
            "Evora",
            "Exige"
          ],

          "Maserati": [
            "430",
            "Biturbo",
            "Coupe",
            "GranSport",
            "GranTurismo",
            "Quattroporte",
            "Spyder"
          ],

          "Mazda": [
            "2",
            "3",
            "5",
            "6",
            "323",
            "626",
            "929",
            "B-Series Pickup",
            "CX-5",
            "CX-7",
            "CX-9",
            "GLC",
            "Miata MX5",
            "Millenia",
            "MPV",
            "MX3",
            "MX6",
            "Navajo",
            "Protege",
            "Protege5",
            "RX-7",
            "RX-8",
            "Tribute"
          ],

          "McLaren": [
            "MP4-12C",
            "GT"
          ],

          "Mercedes-Benz": [
            "190D",
            "190E",
            "240D",
            "300CD",
            "300CE",
            "300D",
            "300E",
            "300TD",
            "300TE",
            "C220",
            "C230",
            "C240",
            "C250",
            "C280",
            "C300",
            "C320",
            "C32 AMG",
            "C350",
            "C36 AMG",
            "C43 AMG",
            "C55 AMG",
            "C63 AMG",
            "CL500",
            "CL550",
            "CL55 AMG",
            "CL600",
            "CL63 AMG",
            "CL65 AMG",
            "CLK320",
            "CLK350",
            "CLK430",
            "CLK500",
            "CLK550",
            "CLK55 AMG",
            "CLK63 AMG",
            "CLS500",
            "CLS550",
            "CLS55 AMG",
            "CLS63 AMG",
            "260E",
            "280CE",
            "280E",
            "400E",
            "500E",
            "E300",
            "E320",
            "E320 Bluetec",
            "E320 CDI",
            "E350",
            "E350 Bluetec",
            "E400 Hybrid",
            "E420",
            "E430",
            "E500",
            "E550",
            "E55 AMG",
            "E63 AMG",
            "G500",
            "G550",
            "G55 AMG",
            "G63 AMG",
            "GL320 Bluetec",
            "GL320 CDI",
            "GL350 Bluetec",
            "GL450",
            "GL550",
            "GLK350",
            "ML320",
            "ML320 Bluetec",
            "ML320 CDI",
            "ML350",
            "ML350 Bluetec",
            "ML430",
            "ML450 Hybrid",
            "ML500",
            "ML550",
            "ML55 AMG",
            "ML63 AMG",
            "R320 Bluetec",
            "R320 CDI",
            "R350",
            "R350 Bluetec",
            "R500",
            "R63 AMG",
            "300SD",
            "300SDL",
            "300SE",
            "300SEL",
            "350SD",
            "350SDL",
            "380SE",
            "380SEC",
            "380SEL",
            "400SE",
            "400SEL",
            "420SEL",
            "500SEC",
            "500SEL",
            "560SEC",
            "560SEL",
            "600SEC",
            "600SEL",
            "S320",
            "S350",
            "S350 Bluetec",
            "S400 Hybrid",
            "S420",
            "S430",
            "S500",
            "S550",
            "S55 AMG",
            "S600",
            "S63 AMG",
            "S65 AMG",
            "300SL",
            "380SL",
            "380SLC",
            "500SL",
            "560SL",
            "600SL",
            "SL320",
            "SL500",
            "SL550",
            "SL55 AMG",
            "SL600",
            "SL63 AMG",
            "SL65 AMG",
            "SLK230",
            "SLK250",
            "SLK280",
            "SLK300",
            "SLK320",
            "SLK32 AMG",
            "SLK350",
            "SLK55 AMG",
            "SLR",
            "SLS AMG",
            "Sprinter"
          ],

          "MG": [
            "4",
            "5",
            "HS",
            "EHS",
            "F",
            "ZS"
          ],

          "Mini": [
            "Cooper Clubman",
            "Cooper S Clubman",
            "Cooper Countryman",
            "Cooper S Countryman",
            "Cooper Coupe",
            "Cooper S Coupe",
            "Cooper",
            "Cooper S",
            "Cooper Roadster",
            "Cooper S Roadster"
          ],

          "Mitsubishi": [
            "3000GT",
            "Cordia",
            "Diamante",
            "Eclipse",
            "Endeavor",
            "Expo",
            "Galant",
            "i",
            "Lancer",
            "Lancer Evolution",
            "Mighty Max",
            "Mirage",
            "Montero",
            "Montero Sport",
            "Outlander",
            "Outlander Sport",
            "Precis",
            "Raider",
            "Sigma",
            "Starion",
            "Tredia",
            "Van"
          ],

          "Nissan": [
            "200SX",
            "240SX",
            "300ZX",
            "350Z",
            "370Z",
            "Altima",
            "Armada",
            "Axxess",
            "Cube",
            "Frontier",
            "GT-R",
            "Juke",
            "Leaf",
            "Maxima",
            "Murano",
            "Murano CrossCabriolet",
            "NV",
            "NX",
            "Pathfinder",
            "Pickup",
            "Pulsar",
            "Quest",
            "Rogue",
            "Sentra",
            "Stanza",
            "Titan",
            "Van",
            "Versa",
            "Xterra"
          ],

          "Opel": [
            "Adam",
            "Ascona",
            "Astra",
            "Campo",
            "Combo",
            "Corsa",
            "Crossland",
            "Frontera",
            "GT",
            "Insignia",
            "Kadett",
            "Tigra",
            "Vectra",
            "Zafira"
          ],

          "Peugeot": [
            "104",
            "106",
            "107",
            "108",
            "204",
            "205",
            "206",
            "207",
            "208",
            "301",
            "304",
            "305",
            "306",
            "307",
            "308",
            "309",
            "408",
            "508",
            "2008",
            "3008",
            "4007",
            "5008"
          ],

          "Pontiac": [
            "1000",
            "6000",
            "Aztek",
            "Bonneville",
            "Catalina",
            "Fiero",
            "Firebird",
            "G3",
            "G5",
            "G6",
            "G8",
            "Grand Am",
            "Grand Prix",
            "GTO",
            "J2000",
            "Le Mans",
            "Montana",
            "Parisienne",
            "Phoenix",
            "Safari",
            "Solstice",
            "Sunbird",
            "Sunfire",
            "Torrent",
            "Trans Sport",
            "Vibe"
          ],

          "Porsche": [
            "911",
            "924",
            "928",
            "944",
            "968",
            "Boxster",
            "Carrera GT",
            "Cayenne",
            "Cayman",
            "Panamera"
          ],

          "Renault": [
            "18i",
            "Fuego",
            "Kadjar",
            "Kangoo",
            "Laguna",
            "Le Car",
            "Megane",
            "R18",
            "Sportwagon",
            "Twingo"
          ],

          "Rolls-Royce": [
            "Camargue",
            "Corniche",
            "Ghost",
            "Park Ward",
            "Phantom",
            "Silver Dawn",
            "Silver Seraph",
            "Silver Spirit",
            "Silver Spur"
          ],

          "Saab": [
            "9-2X",
            "9-3",
            "9-4X",
            "9-5",
            "9-7X",
            "900",
            "9000"
          ],

          "Seat": [
            "Altea",
            "Ateca",
            "Cordoba",
            "Leon",
            "Malaga",
            "Terra",
            "Toledo"
          ],

          "Škoda": [
            "Citigo",
            "Fabia",
            "Favorit",
            "Felicia",
            "Kamiq",
            "Karoq",
            "Octavia",
            "Pick Up",
            "Rapid",
            "Roomster",
            "Superb",
            "Yeti"
          ],

          "Smart": [
            "fortwo",
            "forfour"
          ],

          "Subaru": [
            "Baja",
            "Brat",
            "BRZ",
            "Forester",
            "Impreza",
            "Impreza WRX",
            "Justy",
            "L Series",
            "Legacy",
            "Loyale",
            "Outback",
            "SVX",
            "Tribeca",
            "XT",
            "XV Crosstrek"
          ],

          "Suzuki": [
            "Aerio",
            "Equator",
            "Esteem",
            "Forenza",
            "Grand Vitara",
            "Kizashi",
            "Reno",
            "Samurai",
            "Sidekick",
            "Swift",
            "SX4",
            "Verona",
            "Vitara",
            "X-90",
            "XL7"
          ],

          "Tesla": [
            "Model 3",
            "Model S",
            "Model X",
            "Model Y",
            "Roadster",
          ],

          "Toyota": [
            "4Runner",
            "Avalon",
            "Camry",
            "Celica",
            "Corolla",
            "Corona",
            "Cressida",
            "Echo",
            "FJ Cruiser",
            "Highlander",
            "Land Cruiser",
            "Matrix",
            "MR2",
            "MR2 Spyder",
            "Paseo",
            "Pickup",
            "Previa",
            "Prius",
            "Prius C",
            "Prius V",
            "RAV4",
            "Sequoia",
            "Sienna",
            "Solara",
            "Starlet",
            "Supra",
            "T100",
            "Tacoma",
            "Tercel",
            "Tundra",
            "Van",
            "Venza",
            "Yaris"
          ],

          "Volkswagen": [
            "Beetle",
            "Cabrio",
            "Cabriolet",
            "CC",
            "Corrado",
            "Dasher",
            "Eos",
            "Eurovan",
            "Fox",
            "GLI",
            "Golf R",
            "GTI",
            "Golf",
            "Golf 2",
            "Golf 3",
            "Golf 4",
            "Golf 5",
            "Golf 6",
            "Golf 7",
            "Golf 8",
            "Rabbit",
            "Jetta",
            "Passat",
            "Phaeton",
            "Pickup",
            "Polo",
            "Quantum",
            "R32",
            "Routan",
            "Scirocco",
            "Tiguan",
            "Touareg",
            "Vanagon"
          ],

          "Volvo": [
            "240",
            "260",
            "740",
            "760",
            "780",
            "850",
            "940",
            "960",
            "C30",
            "C70",
            "S40",
            "S60",
            "S70",
            "S80",
            "S90",
            "V40",
            "V50",
            "V70",
            "V90",
            "XC60",
            "XC70",
            "XC90"
          ],

          "Yugo": [
            "45",
            "GV",
            "GVC",
            "GVL",
            "GVS",
            "GVX",
            "Tempo"
          ]

      };


function specsInit() {

	let carList = [
	    "Abarth",
	    "Alfa Romeo",
	    "Aston Martin",
	    "Audi",
	    "Bentley",
	    "BMW",
	    "Bugatti",
	    "Cadillac",
	    "Chevrolet",
	    "Chrysler",
	    "Citroen",
	    "Dacia",
	    "Daewoo",
	    "Dodge",
	    "Ferrari",
	    "Fiat",
	    "Ford",
	    "Honda",
	    "Hummer",
	    "Hyundai",
	    "Infiniti",
	    "Iveco",
	    "Jaguar",
	    "Jeep",
	    "Kia",
	    "Lada",
	    "Lamborghini",
	    "Lancia",
	    "Land Rover",
	    "Lexus",
	    "Lotus",
	    "Maserati",
	    "Mazda",
	    "McLaren",
	    "Mercedes-Benz",
	    "MG",
	    "Mini",
	    "Mitsubishi",
	    "Nissan",
	    "Opel",
	    "Peugeot",
	    "Pontiac",
	    "Porsche",
	    "Renault",
	    "Rolls-Royce",
	    "Saab",
	    "Seat",
	    "Škoda",
	    "Smart",
	    "Subaru",
	    "Suzuki",
	    "Tesla",
	    "Toyota",
	    "Volkswagen",
	    "Volvo",
	    "Yugo"
	];
	let carSelect = document.getElementById("select-brand");
	carList.forEach(function(car) {
		let option = document.createElement("option");
		option.value = car;
		option.text = car;
		carSelect.add(option);
	});

	let countyList = [
	    "Bjelovarsko-bilogorska",
	    "Brodsko-posavska",
	    "Dubrovačko-neretvanska",
	    "Istarska",
	    "Karlovačka",
	    "Koprivničko-križevačka",
	    "Krapinsko-zagorska",
	    "Ličko-senjska",
	    "Međimurska",
	    "Osječko-baranjska",
	    "Požeško-slavonska",
	    "Primorsko-goranska",
	    "Sisačko-moslavačka",
	    "Splitsko-dalmatinska",
	    "Šibensko-kninska",
	    "Varaždinska",
	    "Virovitičko-podravska",
	    "Vukovarsko-srijemska",
	    "Zadarska",
	    "Zagrebačka",
	    "Grad Zagreb"
	];
	let countySelect = document.getElementById("select-county");
	countyList.forEach(function(county) {
		let option = document.createElement("option");
		option.value = county;
		option.text = county;
		countySelect.add(option);
	});

	populateFilterData();
	populateSelectFields();

	backButton.style.display = "none";

	hideSteps();
}

function updateModels() {
	let brandSelect = document.getElementById("select-brand");
	let modelSelect = document.getElementById("select-model");

	let brand = brandSelect.value;

	modelSelect.innerHTML = "";

	modelDict[brand].forEach(function(model) {
		let option = document.createElement("option");
		option.text = model;
		option.value = model;
		modelSelect.add(option);
	});
}

function updateModelsWithAny() {
	let brandSelect = document.getElementById("select-brand");
	let modelSelect = document.getElementById("select-model");

	let brand = brandSelect.value;

	modelSelect.innerHTML = "";

	let option = document.createElement("option");
	option.text = "Any";
	option.value = "any";
	modelSelect.add(option);

	modelDict[brand].forEach(function(model) {
		let option = document.createElement("option");
		option.text = model;
		option.value = model;
		modelSelect.add(option);
	});
}

function disableRegInput() {
	let checkbox = document.getElementById("reg-checkbox");
	let date = document.getElementById("reg-date");

	if (checkbox.checked) {
		date.removeAttribute("required");
		date.value = '0001-01-01';
		date.setAttribute("disabled", "true");
	} else {
		date.removeAttribute("disabled");
		date.setAttribute("required", "true");
	}
}

function showPrevious() {
	if (currentDiv == allFormDivs.length - 1) {
		nextButton.style.display = "block";
	} else if (currentDiv == 1) {
		backButton.style.display = "none";
		document.getElementById("error-msg").innerText = "";
	}

	if (currentDiv > 0) {
		allFormDivs[currentDiv].style.display = "none";
		currentDiv--;
		allFormDivs[currentDiv].style.display = "block";
	}

}

function showPrevious2() {
	var optionalDiv = document.getElementById('additional-optional');
	optionalDiv.style.display = 'none';

	var mandatoryDiv = document.getElementById('additional-mandatory');
	mandatoryDiv.style.display = 'block';
}

function showNext(page) {
	if (page == "specs") {
		let errorMsg = "";

		if (document.getElementById("select-brand").value == "") {
			errorMsg += "No vehicle brand selected\n";
		}

		if (document.getElementById("select-model").value == "") {
			errorMsg += "No vehicle model selected\n";
		}

		if (document.getElementById("engine-power").value == "") {
			errorMsg += "No engine power given\n";
		}

		if (document.getElementById("engine-type").value == "") {
			errorMsg += "No engine type selected\n";
		}

		if (document.getElementById("shifter-type").value == "") {
			errorMsg += "No shifter type selected\n";
		}

		if (document.getElementById("kms").value == "") {
			errorMsg += "No kilometrage given\n";
		}

		if (document.getElementById("year").value == "") {
			errorMsg += "No manufacture year given\n";
		}

		if (document.getElementById("reg-date").value == "" && document.getElementById("reg-checkbox").checked == false) {
			errorMsg += "No registration date selected\n";
		}

		if (document.getElementById("owner").value == "") {
			errorMsg += "No owner number selected\n";
		}

		if (document.getElementById("select-county").value == "") {
			errorMsg += "No location selected\n";
		}

		if (errorMsg != "") {
			document.getElementById("error-msg").innerText = errorMsg;
		}

		if (currentDiv == allFormDivs.length - 2) {
			nextButton.style.display = "none";
		} else if (currentDiv == 0) {
			backButton.style.display = "block";
		}

		if (currentDiv < allFormDivs.length - 1) {
			allFormDivs[currentDiv].style.display = "none";
			currentDiv++;
			allFormDivs[currentDiv].style.display = "block";
		}
	}

	else {

		if (currentDiv == allFormDivs.length - 2) {
			nextButton.style.display = "none";
		} else if (currentDiv == 0) {
			backButton.style.display = "block";
		}

		if (currentDiv < allFormDivs.length - 1) {
			allFormDivs[currentDiv].style.display = "none";
			currentDiv++;
			allFormDivs[currentDiv].style.display = "block";
		}
	}
}

function hideSteps() {
	for (let i = 1; i < allFormDivs.length; i++) allFormDivs[i].style.display = "none";
}

function saveFilterData() {
	let filters = {
		sort: document.getElementById("sort").value,
		brand: document.getElementById("select-brand").value,
		model: document.getElementById("select-model").value,
		minPower: document.getElementById("engine-power-min").value,
		maxPower: document.getElementById("engine-power-max").value,
		engineType: document.getElementById("engine-type").value,
		shifterType: document.getElementById("shifter-type").value,
		distanceMin: document.getElementById("kms-min").value,
		distanceMax: document.getElementById("kms-max").value,
		ageMin: document.getElementById("year-min").value,
		ageMax: document.getElementById("year-max").value,
		owner: document.getElementById("owner").value,
		county: document.getElementById("select-county").value,
		doorCount: document.getElementById("door-count").value,
		gearCount: document.getElementById("gear-count").value,
		bodyShape: document.getElementById("body-shape").value,
		driveType: document.getElementById("drive-type").value,
		fuelMin: document.getElementById("consumption-min").value,
		fuelMax: document.getElementById("consumption-max").value,
		acType: document.getElementById("ac-type").value,
		seatCount: document.getElementById("seat-count").value
	}
	localStorage.setItem("filters", JSON.stringify(filters));
}

function populateFilterData() {
	let filters = localStorage.getItem("filters");
	if (filters && document.getElementById("sort")) {
		filters = JSON.parse(filters);

		document.getElementById("sort").value = filters.sort;
		document.getElementById("select-brand").value = filters.brand;
		updateModelsWithAny();
		document.getElementById("select-model").value = filters.model;
		document.getElementById("engine-power-min").value = filters.minPower;
		document.getElementById("engine-power-max").value = filters.maxPower;
		document.getElementById("engine-type").value = filters.engineType;
		document.getElementById("shifter-type").value = filters.shifterType;
		document.getElementById("kms-min").value = filters.distanceMin;
		document.getElementById("kms-max").value = filters.distanceMax;
		document.getElementById("year-min").value = filters.ageMin;
		document.getElementById("year-max").value = filters.ageMax;
		document.getElementById("owner").value = filters.owner;
		document.getElementById("select-county").value = filters.county;
		document.getElementById("door-count").value = filters.doorCount;
		document.getElementById("gear-count").value = filters.gearCount;
		document.getElementById("body-shape").value = filters.bodyShape;
		document.getElementById("drive-type").value = filters.driveType;
		document.getElementById("consumption-min").value = filters.fuelMin;
		document.getElementById("consumption-max").value = filters.fuelMax;
		document.getElementById("ac-type").value = filters.acType;
		document.getElementById("seat-count").value = filters.seatCount;

		localStorage.removeItem("filters");
	}
}

function populateSelectFields() {
	let brandSelect = document.getElementById("select-brand");
	let modelSelect = document.getElementById("select-model");
	let countySelect = document.getElementById("select-county");

	let brandVal = document.getElementById("populate-brand");
	let modelVal = document.getElementById("populate-model");
	let countyVal = document.getElementById("populate-location");

	if (brandSelect && modelSelect && countySelect && brandVal && modelVal && countyVal) {
		brandSelect.value = brandVal.value;
		updateModels();
		modelSelect.value = modelVal.value;
		countySelect.value = countyVal.value;
	}
}

function submitWithPages(pageNumber){
    let form = document.getElementById("filter-form");
    let page = document.getElementById("filter-page");
    page.value = pageNumber;
    form.submit();
}