package xcontroller

import (
	"KantinHB/xmodel"
	"bytes"
	"fmt"
	"github.com/jasonlvhit/gocron"
	"os"
	"text/template"

	gm "gopkg.in/gomail.v2"
)

func parseTemplate(templateFileName string, data interface{}) (string, error) {
	b := ""
	if IsRedisKeyExists(GetRedisClient(), templateFileName) {
		fmt.Println("parsing from cache")
		b = GetRedis(GetRedisClient(), templateFileName)
	} else {
		fmt.Println("creating new cache")
		bx, err := os.ReadFile(templateFileName) // just pass the file name
		if err != nil {
			return "", err
		}
		b = string(bx)
		SetRedis(GetRedisClient(), templateFileName, b, 0)
	}

	t, err := template.New("").Parse(b)

	if err != nil {
		return "", err
	}

	var buff bytes.Buffer
	if err := t.Execute(&buff, data); err != nil {
		return "", err
	}

	return buff.String(), nil
}

func SendCheckout(transaction xmodel.Transaction, total int) {
	templatexx := "bin/template/checkout.html"

	itmps, _ := GetRandomItems(2)
	templatex := map[interface{}]interface{}{
		"transaction": transaction,
		"total":       total,
		"left":        itmps[0],
		"right":       itmps[1],
	}

	result, errx := parseTemplate(templatexx, templatex)
	if errx == nil {
		SendEmail("Kx5bPjry3gREqQiKkVJrM27f@gmail.com", transaction.User.Email, result, "Receipt")
	} else {
		fmt.Println(errx)
	}
}

func SendNewsletter(user xmodel.User) {

	templatexx := "bin/template/newsletter.html"

	itmps, _ := GetRandomItems(2)
	templatex := map[interface{}]interface{}{
		"user":  user,
		"left":  itmps[0],
		"right": itmps[1],
	}

	result, errx := parseTemplate(templatexx, templatex)
	if errx == nil {
		SendEmail("Kx5bPjry3gREqQiKkVJrM27f@gmail.com", user.Email, result, "Newsletter")
	} else {
		fmt.Println(errx)
	}
}

func SendEmail(from string, to string, body string, subject string) {
	mail := gm.NewMessage()
	mail.SetHeader("From", from)
	mail.SetHeader("To", to)
	mail.SetHeader("Subject", subject)
	mail.SetBody("text/html", body)

	sender := gm.NewDialer("smtp.gmail.com", 25, "Kx5bPjry3gREqQiKkVJrM27f@gmail.com", "tvzyqdonjztrwsod")

	if err := sender.DialAndSend(mail); err != nil {
		fmt.Println(err)
	} else {
		fmt.Println("Email sent! " + from + " -> " + to + " (" + subject + ")")
	}
}

var scheduler = gocron.NewScheduler()

func StartScheduler(user xmodel.User) {
	SendNewsletter(user)
	scheduler.Clear()
	err := scheduler.Every(30).Second().Do(func() {
		SendNewsletter(user)
	})
	if err != nil {
		return
	}
	scheduler.Start()
}

func StopScheduler() {
	scheduler.Clear()
}
