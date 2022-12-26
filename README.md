# Text-to-Pdf-Converter
This solution by which I solved the problem by using simple PdfDocument and StaticLayout without using any external paid libraries. The problem as I have shared in my question is that maintaining proper structure of the texts in the generated pdf file.

I used the length and width of a A4 size page and have fixed the number of characters in each page to a limit, after that when it encounters the end of that line it automatically creates next page, also auto next line is handled by StaticLayout no overflowing of text is seen.

If you feel you can try with different character limit for each page and try different size as your need. Also you can try the StaticLayout.Builder which is supported in Android version Q/29 and above not below it.

The code sample I will share with you, which if you use, no matter how long the text you have, it will automatically handle multi page if text is long and generate the pdf, also it maintains paragraphs etc, you can also customise page shape, number of characters etc according to your need. Other details in the code are self-explanatory.
