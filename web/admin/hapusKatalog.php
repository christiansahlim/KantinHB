<?php
include "../koneksi.php";
include "../function/function.php";
session_start();
$id = $_GET['idKatalog'];
if (hapusKatalogAPI($id) > 0) {
    // echo "<script>
    //    alert('Data Berhasil Dihapus');
    //    </script>
    // ";
} else {
    echo "<script>
       alert('Data Gagal Dihapus');
       </script>
    ";
}
echo '<META HTTP-EQUIV="Refresh" Content="0; URL=katalog.php">';
exit;
?>