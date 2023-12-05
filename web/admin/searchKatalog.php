<?php
if (isset($_POST['search'])) 
{
    require_once '../koneksi.php';

    $search = $_POST['search'];

    $dataKatalog = send_http_request("localhost:8081/items", [], "GET");
?>
    <?php foreach ($dataKatalog["data"] as $d) : ?>
        <?php if (str_contains(strtolower($d['name']), strtolower($search))) : ?>
            <tr>
                <th scope="row"><?= $d['id'] ?></th>
                <td><?= $d['name'] ?></td>
                <td><?= $d['price'] ?></td>
                <td><?= $d['description'] ?></td>
                <td>
                    <img src="<?= $d['image'] ?>" alt="gambar katalog" width="100px">
                </td>
                <td>
                    <a href="hapusKatalog.php?idKatalog=<?= $d['id'] ?>" onclick="return confirm('Confirm deletion')">Delete</a> | <a href="editKatalog.php?idKatalog=<?= $d['id'] ?>">Edit</a>
                </td>
            </tr>
        <?php endif; ?>
    <?php endforeach;
} ?>