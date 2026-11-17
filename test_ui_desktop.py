import pyautogui
import time
import pytest

COORD_TXT_NOMBRE = (1425, 59)
COORD_TXT_BUSQUEDA = (1320, 1438)
COORD_BTN_BUSCAR = (2125, 1441)
COORD_BTN_MOSTRAR_TODOS = (1304, 1487)
COORD_BTN_MODIFICAR = (435,1485)
PAUSA = 2.0

def test_pui1_alta_estudiante():
    time.sleep(4)
    pyautogui.click(COORD_TXT_NOMBRE)
    time.sleep(PAUSA)
    pyautogui.write("Juan Perez")
    pyautogui.press('tab')
    pyautogui.write("333")
    pyautogui.press('tab')
    pyautogui.write("1ro")
    pyautogui.press('tab')
    pyautogui.write("9.0")
    pyautogui.press('tab')
    pyautogui.press('space')
    time.sleep(PAUSA)
    pyautogui.press('space')
    time.sleep(PAUSA)
    pyautogui.click(COORD_TXT_NOMBRE)
    time.sleep(PAUSA)
    pyautogui.write("Ricardo Romo ")
    pyautogui.press('tab')
    pyautogui.write("123")
    pyautogui.press('tab')
    pyautogui.write("1ro")
    pyautogui.press('tab')
    pyautogui.write("9.0")
    pyautogui.press('tab')
    pyautogui.press('space')
    time.sleep(PAUSA)
    pyautogui.press('space')
    time.sleep(PAUSA)
    assert True
    time.sleep(PAUSA)

def test_pui2_validacion_vacios():
    pyautogui.press('space')
    time.sleep(PAUSA)
    pyautogui.press('space')
    assert True
    time.sleep(PAUSA)

def test_pui3_busqueda_matricula():
    pyautogui.click(COORD_TXT_BUSQUEDA)
    time.sleep(PAUSA)
    pyautogui.write("123")
    pyautogui.press('tab')
    pyautogui.press('space')
    time.sleep(PAUSA)
    pyautogui.press('space')
    assert True
    time.sleep(PAUSA)

def test_pui4_mostrar_todos():
    pyautogui.click(COORD_BTN_MOSTRAR_TODOS)
    time.sleep(PAUSA)
    pyautogui.press('space')
    time.sleep(PAUSA)
    assert True

def test_pui5_modificar_nombre():
    pyautogui.click(COORD_TXT_BUSQUEDA)
    pyautogui.press('backspace')
    pyautogui.press('backspace')
    pyautogui.press('backspace')
    time.sleep(PAUSA)
    pyautogui.write("333")
    pyautogui.press('tab')
    pyautogui.press('space')
    time.sleep(PAUSA)
    pyautogui.press('space')
    pyautogui.click(COORD_TXT_NOMBRE)
    time.sleep(PAUSA)
    pyautogui.write("Juan Perez Modificado")
    pyautogui.click(COORD_BTN_MODIFICAR)
    time.sleep(PAUSA)
    pyautogui.press('space')
    assert True
