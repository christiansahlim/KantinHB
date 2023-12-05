<?php
require "../koneksi.php";
require "../function/function.php";

session_start();

if (isset($_SESSION['username']) && $_SESSION['level'] == "admin") {
    echo "<script>
            alert('You haven't Logout');
            </script>
        ";
    echo '<META HTTP-EQUIV="Refresh" Content="0; URL=../admin/">';
}
if (isset($_SESSION['username']) && $_SESSION['level'] == "member") {
    echo "<script>
            alert('You haven't Logout');
            </script>
        ";
    echo '<META HTTP-EQUIV="Refresh" Content="0; URL=../member/">';
}

if (isset($_POST["signin"])) {

    $username = $_POST['username'];
    $password = $_POST['password'];

    $curlResponseHeaderCallback = function ($ch, $headerLine) 
    {
        $cookies = array();
        if (preg_match('/^Set-Cookie:\s*([^;]*)/mi', $headerLine, $cookie) == 1)
        {
            $cookies[] = $cookie;
            $cookie_parsed = [];
            parse_str($cookies[0][1], $cookie_parsed);
            foreach ($cookie_parsed as $key => $value)
            {
                if ($key == "token")
                {
                    $_SESSION["token"] = $value;
                }
            }
        }
        return strlen($headerLine);
    };

    $result = send_http_request(
        "localhost:8081/login", 
        [ "email" => $username, "password" => $password ], 
        "POST", 
        $curlResponseHeaderCallback);
    if ($result["status"] == 200 && $result["data"]["admin"])
    {
        $_SESSION["username"] = $result["data"]["email"];
        $_SESSION["name"] = $result["data"]["name"];
        $_SESSION["level"] = $result["data"]["admin"] ? "admin" : "member";
        $_SESSION["id"] = $result["data"]["id"];
        echo '<META HTTP-EQUIV="Refresh" Content="0; URL=../admin/">';
    }
    else
    {
        echo "<script>
            alert('Wrong username or password!');
            </script>
        ";     
    }
    //var_dump($result);

    // foreach ($listakun as $member) {
    //     if ($username == $member['username'] && $password == $member['password'] && $member['level'] == "member") {
    //         session_start();
    //         $_SESSION['username'] = $username;
    //         $_SESSION['level'] = $member['level'];
    //         $_SESSION['id'] = $member['id'];
    //         echo "<script>
    //             alert('Berhasil masuk halaman Member');
    //             </script>
    //         ";
    //         echo '<META HTTP-EQUIV="Refresh" Content="0; URL=../member/">';
    //     }
    // }
    // foreach ($listakun as $admin) {
    //     if ($username == $admin['username'] && $password == $admin['password'] && $admin['level'] == "admin") {
    //         session_start();
    //         $_SESSION['username'] = $username;
    //         $_SESSION['level'] = $admin['level'];
    //         $_SESSION['id'] = $admin['id'];
    //         echo "<script>
    //             alert('Berhasil masuk halaman Admin');
    //             </script>
    //         ";
    //         echo '<META HTTP-EQUIV="Refresh" Content="0; URL=../admin/">';
    //     }
    // }
}

// var_dump($listakun);
// var_dump($listakun);
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

    <title>Login Page</title>
</head>

<body class="bg-dark">
    <div class="container">
        <div class="row d-flex justify-content-center align-items-center m-0" style="height: 100vh;">
            <div class="login_oueter">
                <div class="col-md-12 logo_outer text-center mb-4">
                    <a href="../index.php">
                        <img src="../asset/img/logo.svg" alt="logo" width="50">
                    </a>
                </div>
                <form action="" method="post" id="login" autocomplete="off" class="bg-light border p-3">
                    <div class="form-row">
                        <h4 class="title my-3 text-center">Login</h4>
                        <div class="col-12">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon1"><i class="fas fa-user"></i></span>
                                </div>
                                <input name="username" type="text" value="" class="input form-control rounded" id="username" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1" />
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon1"><i class="fas fa-lock"></i></span>
                                </div>
                                <input name="password" type="password" value="" class="input form-control rounded" id="password" placeholder="Password" required="true" aria-label="password" aria-describedby="basic-addon1" />
                                <div class="input-group-append">
                                    <span class="input-group-text" onclick="password_show_hide();">
                                        <i class="fas fa-eye" id="show_eye"></i>
                                        <i class="fas fa-eye-slash d-none" id="hide_eye"></i>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <!-- <div class="col-sm-12 pt-3 text-center">
                            <p>Belum Register ? <a href="../signup/">Register</a></p>
                        </div> -->
                        <div class="col-12  text-center">
                            <button class="btn btn-primary" type="submit" name="signin">Login</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    <script src="../script.js"></script>
</body>

</html>