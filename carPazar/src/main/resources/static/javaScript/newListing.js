    function specsInit(){
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
            "Citroën",
            "Dacia",
            "Daewoo",
            "Daihatsu",
            "Dodge",
            "Donkervoort",
            "DS",
            "Ferrari",
            "Fiat",
            "Fisker",
            "Ford",
            "Honda",
            "Hummer",
            "Hyundai",
            "Infiniti",
            "Iveco",
            "Jaguar",
            "Jeep",
            "Kia",
            "KTM",
            "Lada",
            "Lamborghini",
            "Lancia",
            "Land Rover",
            "Landwind",
            "Lexus",
            "Lotus",
            "Maserati",
            "Maybach",
            "Mazda",
            "McLaren",
            "Mercedes-Benz",
            "MG",
            "Mini",
            "Mitsubishi",
            "Morgan",
            "Nissan",
            "Opel",
            "Peugeot",
            "Porsche",
            "Renault",
            "Rolls-Royce",
            "Rover",
            "Saab",
            "Seat",
            "Skoda",
            "Smart",
            "SsangYong",
            "Subaru",
            "Suzuki",
            "Tesla",
            "Toyota",
            "Volkswagen",
            "Volvo"
        ];
        let carSelect = document.getElementById("select-brand");
        carList.forEach(function(car){
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
        countyList.forEach(function(county){
            let option = document.createElement("option");
            option.value = county;
            option.text = county;
            countySelect.add(option);
        });

        hideSteps();
    }


    function disableRegInput(){
        let checkbox = document.getElementById("reg-checkbox");
        let date = document.getElementById("reg-date");

        if(checkbox.checked){
            date.removeAttribute("required");
            date.value = null;
            date.setAttribute("disabled", "true");
        }
        else{
            date.removeAttribute("disabled");
            date.setAttribute("required", "true");
        }
    }

    let currentDiv = 0;
    let allFormDivs = document.querySelectorAll("form > div");

    function showPrevious(){
        if (currentDiv > 0){
            allFormDivs[currentDiv].style.display = "none";
            currentDiv--;
            allFormDivs[currentDiv].style.display = "block";
        }
    }

    function showNext(){
        if (currentDiv < allFormDivs.length - 1){
            allFormDivs[currentDiv].style.display = "none";
            currentDiv++;
            allFormDivs[currentDiv].style.display = "block";
        }
    }

    function hideSteps(){
        for(let i = 1; i < allFormDivs.length; i++)
            allFormDivs[i].style.display = "none";
    }