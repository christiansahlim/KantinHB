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
if (isset($_POST["submit"])) {
    if (tambahKategoriAPI($_POST) > 0) {
        echo "<script>
            alert('Data Berhasil ditambahkan');
        </script>
        ";
        echo '<META HTTP-EQUIV="Refresh" Content="0; URL=kategori.php">';
        exit;
    } else {
        echo "<script>
            alert('Data Gagal ditambahkan');
        </script>
        ";
    }
}
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

    <title>Add Category</title>
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
                    <li class="nav-item">
                        <a class="nav-link" href="cekDataPembelian.php">Transaction</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="katalog.php">Item</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="#">Category</a>
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
    <div class="container text-light" style="margin-top: 100px;">
        <h2 class="text-center text-white" style="margin-top: 150px;">Add Item</h2>
        <form action="" method="post" enctype="multipart/form-data">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="namaPaket">Name</label>
                    <input type="text" class="form-control" id="namaPaket" placeholder="Name" name="name">
                </div>
                <div class="form-group col-md-6">
                    <label for="image">Image</label>
                    <input type="text" class="form-control" id="image" placeholder="Image Link" name="image">
                </div>
                <br>
            </div>
            <button type="submit" class="btn btn-primary" name="submit" data-bs-toggle="modal" data-bs-target="">Submit</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>

</html>