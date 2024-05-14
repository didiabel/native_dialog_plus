import 'package:flutter/material.dart';
import 'package:native_dialog_plus/native_dialog_plus.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Native Dialog Plus Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Native Dialog Plus Demo'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            MaterialButton(
                color: Colors.blue,
                onPressed: () {
                  NativeDialogPlus(
                    actions: [
                      NativeDialogPlusAction(
                        text: 'Now',
                        style: NativeDialogPlusActionStyle.destructive,
                      ),
                      NativeDialogPlusAction(
                        text: "BZ'H",
                        onPressed: () {
                          print("Moshiach vibes");
                        },
                        style: NativeDialogPlusActionStyle.defaultStyle,
                      ),
                    ],
                    title: 'This is a test dialog',
                    message: 'Moshiach NOW',
                  ).show();
                },
                child: const Text('Show basic dialog')),
          ],
        ),
      ),
    );
  }
}
