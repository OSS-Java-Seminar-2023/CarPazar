function openConfirmationDialog(username) {
    var confirmDialog = document.createElement("div");
    confirmDialog.id = "confirmDialog";
    confirmDialog.className = "modal";
    confirmDialog.innerHTML = `
        <div class="modal-content">
            <p>Are you sure you want to delete user: ${username}?</p>
            <button class="delete-button" onclick="deleteUser('${username}')">Yes</button>
            <button class="close-button" onclick="closeConfirmationDialog()">No</button>
        </div>
    `;

    document.body.appendChild(confirmDialog);
}


function closeConfirmationDialog() {
    var dialog = document.getElementById("confirmDialog");
    if (dialog) {
        dialog.parentNode.removeChild(dialog);
    }
}

async function deleteUser(username) {
    const response = await fetch(`/deleteUser/${username}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    });

    if (response.ok) {
        window.location.href = '/adminPanel';
    } else {
        console.error('Deletion failed');
        window.location.href = '/notFound';
    }
}

function deleteUser(username){
    window.location.href = `/deleteUser/${username}`;
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

 function hideUserInfo() {
            var element1 = document.getElementsByClassName('div1')[0];
            var element2 = document.getElementsByClassName('div2')[0];
            element1.style.display = "none"
            element2.style.display = ""
        }

function hidePassChange() {
            var element1 = document.getElementsByClassName('div1')[0];
            var element2 = document.getElementsByClassName('div2')[0];
            element1.style.display = ""
            element2.style.display = "none"
        }