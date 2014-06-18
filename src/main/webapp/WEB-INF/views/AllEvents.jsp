<html>
<head>
    <meta charset="UTF-16">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ticket Service</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <!-- Include Bootstrap Select-->
    <script type="text/javascript" src="js/bootstrap-select.js"></script>
    <link rel="stylesheet" type="text/css" href="css/bootstrap-select.css">
    <script type="text/javascript">
        $(window).on('load', function () {

            $('.selectpicker').selectpicker({
                'selectedText': 'cat'
            });
            // $('.selectpicker').selectpicker('hide');
        });
    </script>

</head>
<body>
<center>
    <div class= "header" style="backgroung-color:red;">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#">Продажа билетов</a></li>
            <li><a href="#">Бронь</a></li>
            <li><a href="#">Возврат билетов</a></li>
            <li><a href="#">Управление</a></li>
        </ul>
    </div>
    <div class= "content" style="margin:15px; width:620px;">
        <div class="buying" style="width:250px; float:left;">
            <div class="panel panel-default">
                <div class="panel-heading" style="text-align:center;">Покупка билета</div>
                <div class="panel-body" style="padding:15px;">
                    <form action="" method="GET">
                        <select class="selectpicker" name="event" data-size="3">
                            <option value="event1">Мероприятие 1</option>
                            <option value="event2">Мероприятие 2</option>
                            <option value="event3">Мероприятие 3</option>
                            <option value="event4">Мероприятие 4</option>
                            <option value="event5">Мероприятие 5</option>
                            <option value="event6">Мероприятие 6</option>
                        </select>
                        <br>
                        <select class="selectpicker" name="sector" data-show-subtext="true">
                            <option value="1" data-subtext="360 грн.">Сектор 1</option>
                            <option value="2" data-subtext="360 грн.">Сектор 2</option>
                            <option value="3" data-subtext="360 грн.">Сектор 3</option>
                            <option value="4" data-subtext="360 грн.">Сектор 4</option>
                            <option value="5" data-subtext="360 грн.">Сектор 5</option>
                            <option value="6" data-subtext="360 грн.">Сектор 6</option>
                        </select>
                        <br>
                        <select class="selectpicker" name="row">
                            <option value="1">Ряд 1</option>
                            <option value="2">Ряд 2</option>
                            <option value="3">Ряд 3</option>
                            <option value="4">Ряд 4</option>
                            <option value="5">Ряд 5</option>
                            <option value="6">Ряд 6</option>
                        </select>
                        <br>
                        <select class="selectpicker" name="place" multiple="true" required="true">
                            <option value="1">Место 1</option>
                            <option value="2">Место 2</option>
                            <option value="3">Место 3</option>
                            <option value="4">Место 4</option>
                            <option value="5">Место 5</option>
                            <option value="6">Место 6</option>
                        </select>
                        <br>
                        <div class="alert alert-info"style="font-weight:bold;">Цена: 360000 UAH</div>
                        <button type="submit" class="btn btn-primary" style="width:100%">Купить</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="legenda" style="width:350px; margin-left: 15px; float:left;" >
            <div class="panel panel-default">
                <div class="panel-heading" style="text-align:center;">Легенда</div>
                <div class="panel-body" style="padding:15px;">
                    <table class="legend" style="width:100%">
                        <thead>
                        <tr>
                            <th>360 грн.</th>
                            <th>400 грн.</th>
                            <th>800 грн.</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1 сект. 360 св.</td>
                            <td>10 сект. 360 св.</td>
                            <td>19 сект. 360 св.</td>
                        </tr>
                        <tr>
                            <td>2 сект. 360 св.</td>
                            <td>11 сект. 360 св.</td>
                            <td>20 сект. 360 св.</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="clearfix"/>
    </div>
</center>

</body>
</html>
