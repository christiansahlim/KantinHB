<?php
include "../koneksi.php";
include "../function/function.php";
session_start();
if (!isset($_SESSION['username'])) {
    echo "<script>
                alert('Anda belum Login');
                </script>
            ";
    echo '<META HTTP-EQUIV="Refresh" Content="0; URL=../login/">';
}
$username = $_SESSION['username'];
$dataPembelian = send_http_request("localhost:8081/transactions", [], "GET");
?>

<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- font awesome  -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous" />

    <!-- Css -->
    <link rel="stylesheet" href="../style/style.css">

    <!-- Poppins font -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">

    <title>Transaction List</title>
</head>

<body class="bg-dark">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="index.php">
                <img src="../asset/img/logo.svg" alt="logo" width="40">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-md-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="index.php">Home</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link active" href="#">Transaction</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="katalog.php">Item</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="kategori.php">Category</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="index.php">Halo <span class="text-info"><?= $username ?></span>!</a>
                    </li>
                </ul>
                <ul class="navbar-nav me-md-auto mb-2 mb-lg-0 navigation" style="margin-left: 45%;">
                    <li class="nav-item me-2">
                        <a class="nav-link btn btn-outline-danger" href="../logout.php" style="margin-left: -100%;">Log Out</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <h2 class="text-center text-white" style="margin-top: 150px;">Transactions</h2>
        <input type="text" id="search" class="form-control mt-3 mb-5" placeholder="Search Customer">
        <table class="table table-dark table-striped" >
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Customer</th>
                    <th scope="col">Status</th>
                    <th scope="col">Method</th>
                    <th scope="col">Price</th>
                </tr>
            </thead>
            <tbody id="tampil">
                <?php foreach($dataPembelian["data"] as $d) : ?>
                <?php
                    $pricex = 0;
                    foreach($d['items'] as $dx)
                    {
                        $pricex += $dx["item"]["price"] * $dx["quantity"];
                    }
                    ?>
                <tr>
                    <th scope="row"><?= $d['id'] ?></th>
                    <td><?= $d['user']['email'] ?></td>
                    <td><?= $d['status'] ?></td>
                    <td><?= $d['method'] ?></td>
                    <td><?= $pricex ?></td>
                </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js" charset="utf-8"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#search').on('keyup', function() {
                $.ajax({
                    type: 'POST',
                    url: 'searchDataPembelian.php',
                    data: {
                        search: $(this).val()
                    },
                    cache: false,
                    success: function(data) {
                        $('#tampil').html(data);
                    }
                });
            });
        });
    </script>
</body>

</html>