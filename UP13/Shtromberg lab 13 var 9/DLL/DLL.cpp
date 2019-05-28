// DLL.cpp: ���������� ���������������� ������� ��� ���������� DLL.
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


	// ������� ������ �������� � ����� ������.

	coord.X = 15;            // ������ �� ������ ������
	coord.Y = 15;            // ������ ������

	fSuccess = WriteConsoleOutputCharacter(
		hStdOut,              // ���������� ��������� ������
		lpszString,           // ��������� �� ������ - ��������
		lstrlen(lpszString),  // ����� ������
		coord,                // ������ ������ ��� ������
		&cWritten);           // �������������� ����� �������




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
	SetConsoleCursorInfo(hStdOut, &cursor);  //���������� �������� ���������� �������
}

