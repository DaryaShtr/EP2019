// DLL.cpp: определяет экспортированные функции для приложения DLL.
//

#include "stdafx.h"
#include "Main.h"
#include <iostream>
#include <string>
using namespace std;
JNIEXPORT void JNICALL Java_Main_Function(JNIEnv *, jclass)
{
	//Get a console handle
	HWND myconsole = GetConsoleWindow();
	//Get a handle to device context
	HANDLE hStdOut = GetStdHandle(STD_OUTPUT_HANDLE);
	HDC mydc = GetDC(myconsole);
	SetConsoleTextAttribute(hStdOut, FOREGROUND_GREEN);
	printf("Green\n");
	SetConsoleTitle(L"NEW NAME");


	LPTSTR lpszString = L"Good";
	DWORD cWritten;
	BOOL fSuccess;
	COORD coord;


	// Запишем строку символов в буфер экрана.

	coord.X = 15;            // начало на первую ячейку
	coord.Y = 15;            // первой строки

	fSuccess = WriteConsoleOutputCharacter(
		hStdOut,              // дескриптор экранного буфера
		lpszString,           // указатель на строку - источник
		lstrlen(lpszString),  // длина строки
		coord,                // первая ячейка для записи
		&cWritten);           // действительное число записей




	COORD here;
	here.X = 5;
	here.Y = 5;
	WORD attribute = FOREGROUND_RED;
	DWORD written;
	::WriteConsoleOutputAttribute(hStdOut, &attribute, 1, here, &written);
	::WriteConsoleOutputCharacterA(hStdOut, "H", 1, here, &written);
	here.X = 6;
	::WriteConsoleOutputAttribute(hStdOut, &attribute, 1, here, &written);
	::WriteConsoleOutputCharacterA(hStdOut, "I", 1, here, &written);

	here.X = 5;
	here.Y = 9;
	SetConsoleCursorPosition(hStdOut, here);


	CONSOLE_CURSOR_INFO cursor = { 1, false };
	SetConsoleCursorInfo(hStdOut, &cursor);  //Применение заданных параметров курсора
}

