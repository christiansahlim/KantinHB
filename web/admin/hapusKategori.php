<?php
include "../koneksi.php";
include "../function/function.php";
session_start();
$id = $_GET['idKategori'];
if (hapusKategoriAPI($id) > 0) {
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
echo '<META HTTP-EQUIV="Refresh" Content="0; URL=kategori.php">';
exit;
?>