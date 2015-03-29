# TeXlipse

Please note that this is NOT the official TeXlipse distribution. This is an personal fork that incorporates a number of minor fixes for bugs/inconveniences I've come across while working with TeXlipse.

The official TeXlipse distribution can be found at http://texlipse.sourceforge.net/

## Setting up

 * Import from source or install from the following update site: https://raw.githubusercontent.com/kolovos/texlipse/master/net.sourceforge.texlipse.updatesite/site.xml
 * Turn on soft-wrapping through `Preferences->TeXlipse->Editor->Use soft wrapping`
 * Point to TeX distribution through `Preferences->TeXlipse->Builded settings`
 * Turn off line highlighting through `Preferences->General->Editors->Text Editors->Highlight current line`

## Navigating from PDF->TeX using Skim

 * Go to `Preferences->TeXlipse->Builder settings` and add the following argument to PdfLatex: `-synctex=1`
 * To navigate back from compiled PDFs in Skim, go to Skim's `Preferences->Sync`, change Preset to `Custom`, set Command to `curl` and Arguments to `localhost:6789?"%file"-"%line"`
