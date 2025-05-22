$(document).ready(function () {
    document.getElementById("createButton").addEventListener('click', getships)
});

function createPcField(){
    document.getElementById("campoComp").innerHTML = '';
    for (let i = 0; i < 100; i++) {
        cell = document.createElement("div");
        cell.addEventListener('click', function (event) {
            attack(event);
        });
        cell.classList.add("cell");
        cell.setAttribute('data-index', i);
        document.getElementById("campoComp").appendChild(cell);
    }
}

function createPlayerField(){
    document.getElementById("campoUtente").innerHTML = '';
    for (let i = 0; i < 100; i++) {
        cell = document.createElement("div");
        cell.classList.add("cell");
        cell.setAttribute('data-index', i);
        document.getElementById("campoUtente").appendChild(cell);
    }
}
function attack(event) {
    let target = event.target;
    target.style.pointerEvents = 'none';
    let x = Math.floor(event.target.dataset.index / 10);
    let y = event.target.dataset.index % 10;
    
    $.ajax({
        url: 'attackships/' + x + '/' + y,
        method: 'GET',
        success: function (response) {
            console.log(response);
            if(response){
                target.classList.add('hit');
            }else{
                target.classList.add('water');
            }
        },
        error: function () {
            alert("Errore nell'attacco tu");
        }
    });

    pcAttack();
}

function pcAttack() {
    $.ajax({
        url: 'attackpcships',
        method: 'GET',
        success: function (response) {
            console.log(response);
            let x = response.coordinateAttack.x;
            let y = response.coordinateAttack.y;
            let index = x * 10 + y;
            let cell = $('#campoUtente .cell').eq(index);
            
            if(response.hasHit){
                cell.addClass('hit');
            } else {
                cell.addClass('water');
            }
        },
        error: function () {
            alert('Errore nell\'attacco pc');
        }
    });
}

function pcAttack() {
    $.ajax({
        url: 'haswon',
        method: 'GET',
        success: function (response) {
            if(response)
        },
        error: function () {
            alert('Errore nell\'attacco pc');
        }
    });
}

function getships() {
    createPcField();
    createPlayerField();
    $.ajax({
        url: 'getships',
        method: 'GET',
        success: function (response) {
            console.log(response);
            let num = 1;
            response.playerShips.forEach(ship => {
                ship.cordinates.forEach(coord => {
                    let index = coord.x * 10 + coord.y;
                    $('#campoUtente .cell').eq(index).addClass('ship-' + num);
                });
                num++;
            });
            /*num = 1;
            response.pcShips.forEach(ship => {
                ship.cordinates.forEach(coord => {
                    let index = coord.x * 10 + coord.y;
                    $('#campoComp .cell').eq(index).addClass('ship-' + num);
                });
                num++;
            });*/
            
            /*
            response.pcShips.forEach(index => {
                $('#campoUtente .cell').eq(index).addClass('ship');
            });
            response.playerShips.forEach(index => {
                $('#campoComp .cell').eq(index).addClass('ship');
            });
            */
        },
        error: function () {
            alert('Errore nel caricamento delle griglie!');
        }
    });
}