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

