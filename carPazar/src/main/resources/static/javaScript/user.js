 function hideUserInfo() {
            var element1 = document.getElementsByClassName('div1')[0];
            var element2 = document.getElementsByClassName('div2')[0];
            element1.style.display = "none"
            element2.style.display = ""
        }
        function hidePassChange() {
            var element1 = document.getElementById('div1');
            var element2 = document.getElementById('div2');
            element1.style.display = ""
            element2.style.display = "none"
        }
        function checkPass(){
                var oldPassword = document.getElementById('old_password').value;
                var newPassword = document.getElementById('new_pass').value;
                var newPassword2 = document.getElementById('new_pass2').value;
                var newPasswordError = document.getElementById('newPasswordError');

                if (newPassword !== newPassword2) {
                    newPasswordError.textContent = 'New passwords do not match.';
                    event.preventDefault();
                    return false;
                }
                else {
                    newPasswordError.textContent = ' ';
                }

                if (newPassword === oldPassword) {
                    samePasswordError.textContent = 'New password cannot be the same as your old password!';
                    event.preventDefault();
                    return false;
                }
                else {
                    samePasswordError.textContent = ' ';
                }

                return true;

        }

        function checkPassNew(){
                        var newPassword = document.getElementById('new_pass').value;
                        var newPassword2 = document.getElementById('new_pass2').value;
                        var newPasswordError = document.getElementById('newPasswordError');

                        if (newPassword !== newPassword2) {
                            newPasswordError.textContent = 'New passwords do not match.';
                            event.preventDefault();
                            return false;
                        }
                        else {
                            newPasswordError.textContent = ' ';
                        }
                        return true;

                }

       function change1() {
            document.getElementById("button1").classList.add("activeButton");
            document.getElementById("button1").classList.remove("normalButton");

            document.getElementById("button2").classList.add("normalButton");
            document.getElementById("button2").classList.remove("activeButton");
       }

       function change2() {
            document.getElementById("button2").classList.add("activeButton");
            document.getElementById("button2").classList.remove("normalButton");

            document.getElementById("button1").classList.add("normalButton");
            document.getElementById("button1").classList.remove("activeButton");
       }