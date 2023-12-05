<?php

function query($query)
{
    global $conn;
    $result = mysqli_query($conn, $query);
    $rows = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $rows[] = $row;
    }
    return $rows;
}

function tambahAkun($data)
{
    global $conn;

    $username = htmlspecialchars($data["username"]);
    $email = htmlspecialchars($data["email"]);
    $password = htmlspecialchars($data["password"]);

    $query = "INSERT INTO akun VALUES (
           '',
           '$username',
           '$email',
           '$password',
           'member'
        )";
    mysqli_query($conn, $query);
    return mysqli_affected_rows($conn);
}

function tambahPembelian($data, $idKatalog)
{
    global $conn;

    $nama_pembeli = htmlspecialchars($data["nama_pembeli"]);
    $id_mobile_legend = htmlspecialchars($data["id_mobile_legend"]);
    $metode_pembayaran = htmlspecialchars($data["metode_pembayaran"][0]);

    setcookie("nama_pembeli", $nama_pembeli, time() + (86400 * 30), "/");
    setcookie("id_mobile_legend", $id_mobile_legend, time() + (86400 * 30), "/");

    $query = "INSERT INTO pembelian VALUES (
           '',
           '$nama_pembeli',
           '$id_mobile_legend',
           '$metode_pembayaran',
           '$idKatalog'
        )";
    mysqli_query($conn, $query);
    return mysqli_affected_rows($conn);
}

function tambahInfoOrder($idKatalog, $nama_pembeli, $id_mobile_legend, $metode_pembayaran, $nama_paket, $harga, $diamond)
{
    global $conn;
    $query = "INSERT INTO info_order VALUES (
           '$idKatalog',
           '$nama_pembeli',
           '$id_mobile_legend',
           '$metode_pembayaran',
           '$nama_paket',
           '$harga',
           '$diamond'
        )";
    mysqli_query($conn, $query);
    return mysqli_affected_rows($conn);
}

function tambahKatalog($data)
{
    global $conn;

    $nama_paket = $data["nama_paket"];
    $harga = $data["harga"];
    $diamond = $data["diamond"];
    $gambar = upload();
    if (!$gambar) {
        return false;
    }

    $query = "INSERT INTO katalog VALUES (
           '',
           '$nama_paket',
           '$harga',
           '$diamond',
           '$gambar'
        )";
    mysqli_query($conn, $query);
    return mysqli_affected_rows($conn);
}

function upload()
{
    $namaFile = $_FILES["image"]["name"];
    $ukuranFile = $_FILES["image"]["size"];
    $error = $_FILES["image"]["error"];
    $tmpName = $_FILES["image"]["tmp_name"];

    // cek apakah tidak ada gambar yang di upload
    if ($error === 4) {
        echo "<script>
             alert('Pilih gambar terlebih dahulu');
          </script>
       ";
        return false;
    }

    // cek apakah yang di upload adalah gambar
    $ekstensiGambarValid = ['jpg', 'jpeg', 'png'];
    $ekstensiGambar = explode('.', $namaFile);
    $ekstensiGambar = strtolower(end($ekstensiGambar));
    if (!in_array($ekstensiGambar, $ekstensiGambarValid)) {
        echo "<script>
             alert('Yang anda upload bukan gambar');
          </script>
       ";
        return false;
    }

    // jika ukuran nya terlalu besar
    if ($ukuranFile > 100000000000) {
        echo "<script>
             alert('Ukuran gambar terlalu besar ');
          </script>
       ";
        return false;
    }

    // generate nama file baru
    $namaFileBaru = uniqid();
    $namaFileBaru .= $ekstensiGambar;

    // lolos pengecekan, gambar siap diupload
    move_uploaded_file($tmpName, '../asset/img/' . $namaFileBaru);
    return $namaFileBaru;
}

function hapusKatalog($id)
{
    global $conn;
    mysqli_query($conn, "DELETE FROM katalog WHERE idKatalog = $id");
    return mysqli_affected_rows($conn);
}

function ubahKatalog($data, $id)
{
    global $conn;
    // $id = $_GET["id"];
    $nama_paket = $data["nama_paket"];
    $harga = $data["harga"];
    $diamond = $data["diamond"];
    $gambarLama = $data["gambarLama"];

    // cek apakah user pilih gambar baru atau tidak
    if ($_FILES['image']['error'] === 4) {
        $gambar = $gambarLama;
    } else {
        $gambar = upload();
    }

    $query = "UPDATE katalog SET
       nama_paket = '$nama_paket',
       harga = '$harga',
       diamond = '$diamond',
       image = '$gambar' 
       WHERE idKatalog = $id
    ";
    mysqli_query($conn, $query);
    return mysqli_affected_rows($conn);
}

function get_cookie_safely($n) {
    if(!isset($_COOKIE[$n])) {
        return "";
    } else {
        return $_COOKIE[$n];
    }
}

function tambahKatalogAPI($data)
{
    $result = send_http_request(
        "localhost:8081/item", 
        [ 
            "name" => $data["name"], 
            "price" => $data["harga"],
            "description" => $data["description"],
            "image" => $data["image"],
            "category_id" => $data["category"]
        ], 
        "POST");        
    return $result["status"] == 200;
}

function ubahKatalogAPI($data, $id)
{
    $result = send_http_request(
        "localhost:8081/item", 
        [ 
            "id" => $id, 
            "name" => $data["name"], 
            "price" => $data["harga"],
            "description" => $data["description"],
            "image" => $data["image"],
            "category_id" => $data["category"]
        ], 
        "PUT");    
    var_dump($result);    
    return $result["status"] == 200;
}

function hapusKatalogAPI($id)
{
    $result = send_http_request(
        "localhost:8081/item", 
        [ "id" => $id ], 
        "DELETE");   
    var_dump($result);
    return $result["status"] == 200; 
}

function hapusKategoriAPI($id)
{
    $result = send_http_request(
        "localhost:8081/category", 
        [ "id" => $id ], 
        "DELETE");   
    var_dump($result);
    return $result["status"] == 200; 
}

function tambahKategoriAPI($data)
{
    $result = send_http_request(
        "localhost:8081/category", 
        [ 
            "name" => $data["name"], 
            "image" => $data["image"]
        ], 
        "POST");        
    return $result["status"] == 200;
}

function ubahKategoriAPI($data, $id)
{
    $result = send_http_request(
        "localhost:8081/category", 
        [ 
            "id" => $id, 
            "name" => $data["name"], 
            "image" => $data["image"]
        ], 
        "PUT");    
    var_dump($result);    
    return $result["status"] == 200;
}

