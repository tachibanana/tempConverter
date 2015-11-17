package com.test.temp
import java.awt.event.ActionListener

import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.*

@Grab('com.github.groovy-wslite:groovy-wslite:1.1.2')
import wslite.soap.*

import groovy.swing.SwingBuilder
import groovy.swing.factory.ComboBoxFactory;

class TempConverter {

	static main(args) {
		SwingBuilder sb = new SwingBuilder()
		sb.edt{
				panel = new JPanel()
				frame = new JFrame(title:"Temperature Converter",
					defaultCloseOperation:JFrame.EXIT_ON_CLOSE,
					size:[260,150],
					locationRelativeTo:null,layout:flowLayout())
				
				panel = frame.contentPane
				
				textField = new JTextField(columns:20)
				
				comboBox = new JComboBox()
				comboBox.addItem("Celsius to Fahrenheit")
				comboBox.addItem("Fahrenheit to Celsius")
				
				button = new JButton(text:"Convert")
				button.addActionListener({
					if(textField.equals("") && !textField.isNumeric())
						JOptionPane.showMessageDialog(frame , "Fill in the blank")
					else{
						if(comboBox.getSelectedIndex() == 0){
							JOptionPane.showMessageDialog(frame, new TempConverter().convertCelsiusToFahrenheit(textField.text))
						}else
							JOptionPane.showMessageDialog(frame, new TempConverter().convertFahrenheitToCelsius(textField.text))
					}		
				} as ActionListener)
				
				panel.add(textField)
				panel.add(comboBox)
				panel.add(button)
				frame.show()
				
				
				
			
		}
		println new TempConverter().convertCelsiusToFahrenheit(3)
		println new TempConverter().convertFahrenheitToCelsius(37.4)
	}
	
	def convertCelsiusToFahrenheit(def celsius){
		def client = new SOAPClient('http://www.w3schools.com/webservices/tempconvert.asmx')
		def response = client.send("""<?xml version="1.0" encoding="utf-8"?>
			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.w3schools.com/webservices/">
			<soapenv:Header/>
			<soapenv:Body>
			<web:CelsiusToFahrenheit>
			<!--Optional:-->
        	<web:Celsius>${celsius}</web:Celsius>
			</web:CelsiusToFahrenheit>
			</soapenv:Body>
			</soapenv:Envelope>
			""")
		
		 response.CelsiusToFahrenheitResponse.CelsiusToFahrenheitResult
		
		
	}
	
	def convertFahrenheitToCelsius(def fahrenheit){
		def client = new SOAPClient('http://www.w3schools.com/webservices/tempconvert.asmx')
		def response = client.send("""
			<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.w3schools.com/webservices/">
			<soapenv:Header/>
			<soapenv:Body>
			<web:FahrenheitToCelsius>
         	<!--Optional:-->
         	<web:Fahrenheit>${fahrenheit}</web:Fahrenheit>
			</web:FahrenheitToCelsius>
			</soapenv:Body>
			</soapenv:Envelope>
			""")
		
		response.FahrenheitToCelsiusResponse.FahrenheitToCelsiusResult
	}

}
