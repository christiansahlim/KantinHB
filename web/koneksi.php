<?php  
    function send_http_request($urlx, $data, $method, $headerCallback = null)
    {
        $url = ($method == "GET" || $method == "DELETE") 
            ? ($urlx . '?' . http_build_query($data))
            : ($urlx);

        $curl = curl_init($url);

        if ($headerCallback == null)
        {
            $headerCallback = function ($ch, $h) { return strlen($h); };
        }

        curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl, CURLOPT_HEADERFUNCTION, $headerCallback);

        if ($method == "POST")
        {
            curl_setopt($curl, CURLOPT_POST, true);
            curl_setopt($curl, CURLOPT_POSTFIELDS, http_build_query($data));
        }
        elseif ($method == "PUT")
        {
            curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "PUT");;
            curl_setopt($curl, CURLOPT_POSTFIELDS, http_build_query($data));
        }
        elseif ($method == "GET")
        {
            curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "GET");
        }
        elseif ($method == "DELETE")
        {
            curl_setopt($curl, CURLOPT_CUSTOMREQUEST, "DELETE");
        }

        if (isset($_SESSION["token"]))
        {
            curl_setopt($curl, CURLOPT_HTTPHEADER, array("Cookie: token=" . $_SESSION["token"]));
        }
        
        $response = curl_exec($curl);
        curl_close($curl);
        return json_decode($response, true);
    }
?>