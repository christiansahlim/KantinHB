<?php
session_start();
if (isset($_POST['search'])) 
{
    require_once '../koneksi.php';

    $search = $_POST['search'];

    $dataPembelian = send_http_request("localhost:8081/transactions", [], "GET");
?>

    <?php foreach($dataPembelian["data"] as $d) : ?>
        <?php if (str_contains(strtolower($d['user']['email']), strtolower($search))) : ?>
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
        <?php endif; ?>
    <?php endforeach; ?>
<?php 
}
?>