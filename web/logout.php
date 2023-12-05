<?php
    session_start();
    $_SESSION['username'] = '';
    unset($_SESSION['username']);
    session_unset();
    session_destroy();
    setcookie("token", "", time() - 3600, "http://localhost/");
    echo '<META HTTP-EQUIV="Refresh" Content="0; URL=login/">';
?>