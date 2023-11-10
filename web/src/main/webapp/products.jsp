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
</head>
<body>
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

    <div class="profile-name-container">
        <span class="profile-brand">Profile</span>
    </div>

    <div class="data-container">
        <table id="products" class="table">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Category</th>
                <th scope="col">Amount</th>
                <th scope="col">Unit</th>
                <th scope="col">Purchase Price</th>
                <th scope="col">Sell Price</th>
                <th scope="col">Description</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${products}">

                <tr>
                    <td>
                        <c:out value="${product.name}"></c:out>
                    </td>

                    <td>
                        <c:out value="${product.category}"></c:out>
                    </td>

                    <td>
                        <c:out value="${product.amount}"></c:out>
                    </td>

                    <td>
                        <c:out value="${product.unit}"></c:out>
                    </td>

                    <td>
                        <c:out value="${product.purchasePrice}"></c:out>
                    </td>

                    <td>
                        <c:out value="${product.sellPrice}"></c:out>
                    </td>

                    <td>
                        <c:out value="${product.description}"></c:out>
                    </td>
                </tr>

            </c:forEach>

            </tbody>

        </table>
        <script>
            $(document).ready(function () {
                $('#products').DataTable();
            });
        </script>
    </div>
</div>
</body>
</html>
