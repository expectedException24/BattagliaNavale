$(document).ready(function () {
    document.getElementById("createButton").addEventListener('click', getships)
});

function createField(field) {
    document.getElementById(field).innerHTML = '';
    for (let i = 0; i < 100; i++) {
        cell = document.createElement("div");
        cell.addEventListener('click', function () {
            attack(event);
        });
        cell.classList.add("cell");
        cell.setAttribute('data-index', i);
        document.getElementById(field).appendChild(cell);
    }
}

function attack(event) {
    
    let x =Math.floor( event.target.dataset.index / 10);
    let y = event.target.dataset.index % 10;
    //console.log(coordinate);
    $.ajax({
        url: 'attackships/'+x+'/'+y,
        method: 'GET',
        success: function (response) {
            console.log(response);
            
        },
        error: function () {
            alert('Errore nell*attacco');
        }
    });
}


function getships() {
    createField("campoUtente");
    createField("campoComp");
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
            num = 1;
            response.pcShips.forEach(ship => {
                ship.cordinates.forEach(coord => {
                    let index = coord.x * 10 + coord.y;
                    $('#campoComp .cell').eq(index).addClass('ship-' + num);
                });
                num++;
            });
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