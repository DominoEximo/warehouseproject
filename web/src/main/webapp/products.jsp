<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<html>
<head>
    <title>Products</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables.css">
    <link rel="stylesheet" type="text/css" href="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/css/jquery.dataTables_themeroller.css">
    <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" charset="utf8" src="http://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.0/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" href="../css/products.css"/>
    <script>
        $(document).ready(function(){
            $("#myInput").on("keyup", function() {
                var value = $(this).val().toLowerCase();
                $("#products tr").filter(function() {
                    $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
                });
            });
        });
    </script>
</head>
<body id="productBody">
<div id='loader'><img src="/images/ZZ5H.gif" alt="Loading..."/></div>
<div id='loaderOverlay'></div>
<div class="outer-container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">Warehouse</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse optionList" id="navbarSupportedContent">

            <form class="form-inline my-2 my-lg-0">
                <a class="nav-link" href="products.jsp">Products <span class="sr-only">(current)</span></a>
                <a class="nav-link" href="#">Orders <span class="sr-only">(current)</span></a>
                <a class="nav-link" href="/secured/profile.html">Profile <span class="sr-only">(current)</span></a>
            </form>
        </div>
    </nav>
    <div class="label-container">
        <div class="profile-name-container">
            <span class="profile-brand">Products</span>
        </div>
        <div>
            <input id="myInput" type="text" placeholder="Search..">
            <input type="number" class="form-control" id="length" placeholder="Length" value="5"/>
            <select name="categories" id="categories">
            </select>
            <select name="units" id="units">
            </select>
        </div>
        <div>
            <button id="refresh" type="button"  class="refresh-button btn btn-dark">Refresh</button>
        </div>
        <div id="overlay">
            <div class="cv-spinner">
                <span class="spinner"></span>
            </div>
        </div>
    </div>
    <div class="data-container">
        <table id="products" class="table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Amount</th>
                    <th>Unit</th>
                    <th>Purchase price</th>
                    <th>Sell price</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody id="table-body">

            </tbody>

        </table>
        <div>
            <button id="prev" type="button"  class=" btn btn-dark">Prev</button>
            <button id="next" type="button"  class=" btn btn-dark">Next</button>
        </div>
        <script>
            $(document).ready(function ()
            {
                var page = 1;

                $("#next").on('click',function () {

                    page += 1;
                    var x = document.getElementById("loaderOverlay");
                    var y = document.getElementById("loader");
                    var length = document.getElementById("length").value;
                    var category = document.getElementById("categories").value;
                    x.style.display = "block";
                    y.style.display = "block";
                    setTimeout(function (){

                        $.getJSON("/api/ProductService/getProducts/"+page+"/"+length+ "/"+ category, function(data) {
                            var productList = '';
                            $.each(data, function(key, value) {
                                productList += '<tr id="rowVehicleStatus" class="">';
                                productList += '<td>'+value.name+'</td>';
                                productList += '<td>'+value.category.categoryName+'</td>';
                                productList += '<td>'+value.amount+'</td>';
                                productList += '<td>'+value.unit.unitName+'</td>';
                                productList += '<td>'+value.purchasePrice+'</td>';
                                productList += '<td>'+value.sellPrice+'</td>';
                                productList += '<td>'+value.description+'</td>';
                                productList += '</tr>';
                            });

                            $('#products').html(productList);


                        }).done(function (){
                            x.style.display = "none";
                            y.style.display = "none";
                        });
                    },2000);
                })
                $("#prev").on('click',function () {
                    if (page == 1){
                        $("#prev").disable();
                    }
                    page -= 1;
                    var x = document.getElementById("loaderOverlay");
                    var y = document.getElementById("loader");
                    var length = document.getElementById("length").value;
                    var category = document.getElementById("categories").value;
                    x.style.display = "block";
                    y.style.display = "block";
                    setTimeout(function (){

                        $.getJSON("/api/ProductService/getProducts/"+page+"/"+length+ "/"+ category, function(data) {
                            var productList = '';
                            $.each(data, function(key, value) {
                                productList += '<tr id="rowVehicleStatus" class="">';
                                productList += '<td>'+value.name+'</td>';
                                productList += '<td>'+value.category.categoryName+'</td>';
                                productList += '<td>'+value.amount+'</td>';
                                productList += '<td>'+value.unit.unitName+'</td>';
                                productList += '<td>'+value.purchasePrice+'</td>';
                                productList += '<td>'+value.sellPrice+'</td>';
                                productList += '<td>'+value.description+'</td>';
                                productList += '</tr>';
                            });

                            $('#products').html(productList);


                        }).done(function (){
                            x.style.display = "none";
                            y.style.display = "none";
                        });
                    },2000);
                })



                $.getJSON("/api/ProductService/getCategories/",function (data){
                    var categoryList = '';
                    $.each(data, function (key,value){
                        categoryList += '<option value=' + value.id + ">" + value.categoryName + '</option>';
                    })
                    $("#categories").html(categoryList);
                })

                $.getJSON("/api/ProductService/getUnits/",function (data){
                    var unitList = '';
                    $.each(data, function (key,value){
                        unitList += '<option value=' + value.id + ">" + value.unitName + '</option>';
                    })
                    $("#units").html(unitList);
                })

                $("#refresh").on('click',function () {
                    var x = document.getElementById("loaderOverlay");
                    var y = document.getElementById("loader");
                    var length = document.getElementById("length").value;
                    var category = document.getElementById("categories").value;
                    x.style.display = "block";
                    y.style.display = "block";
                    setTimeout(function (){

                        $.getJSON("/api/ProductService/getProducts/"+page+"/"+length+ "/"+ category, function(data) {
                            var productList = '';
                            $.each(data, function(key, value) {
                                productList += '<tr id="rowVehicleStatus" class="">';
                                productList += '<td>'+value.name+'</td>';
                                productList += '<td>'+value.category.categoryName+'</td>';
                                productList += '<td>'+value.amount+'</td>';
                                productList += '<td>'+value.unit.unitName+'</td>';
                                productList += '<td>'+value.purchasePrice+'</td>';
                                productList += '<td>'+value.sellPrice+'</td>';
                                productList += '<td>'+value.description+'</td>';
                                productList += '</tr>';
                            });

                            $('#products').html(productList);


                        }).done(function (){
                            x.style.display = "none";
                            y.style.display = "none";
                        });
                    },2000);


                });
                $(document).on({
                    ajaxStart: function(){

                    },
                    ajaxStop: function(){
                        $("body").removeClass("loading");
                    }
                });
            });
        </script>
    </div>
</div>
</body>
</html>
