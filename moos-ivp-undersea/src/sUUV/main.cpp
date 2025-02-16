/************************************************************/
/*    NAME: Simos Gerasimou                                              */
/*    ORGN: MIT                                             */
/*    FILE: main.cpp                                        */
/*    DATE:                                                 */
/************************************************************/

#include <string>
#include "MBUtils.h"
#include "ColorParse.h"
#include "UUV.h"
#include "UUV_Info.h"
#include "Utilities.h"

using namespace std;

int main(int argc, char *argv[]) {
    string mission_file;
    string run_command = argv[0];

    cout << "Starting" << endl;

    for (int i = 1; i < argc; i++) {
        string argi = argv[i];
        if ((argi == "-v") || (argi == "--version") || (argi == "-version"))
            showReleaseInfoAndExit();
        else if ((argi == "-e") || (argi == "--example") || (argi == "-example"))
            showExampleConfigAndExit();
        else if ((argi == "-h") || (argi == "--help") || (argi == "-help"))
            showHelpAndExit();
        else if ((argi == "-i") || (argi == "--interface"))
            showInterfaceAndExit();
        else if (strEnds(argi, ".moos") || strEnds(argi, ".moos++"))
            mission_file = argv[i];
        else if (strBegins(argi, "--alias="))
            run_command = argi.substr(8);
        else if (i == 2)
            run_command = argi;
    }

    if (mission_file == "")
        showHelpAndExit();

    cout << termColor("green");
    cout << "sUUV launching as " << run_command << endl;
    cout << termColor() << endl;

    UUV UUV;

    cout << "sUUV launching as: " << run_command << endl;
    cout << "sUUV mission file: " << mission_file << endl;

//    run_command = "sUUValpha";
//    mission_file = "/Volumes/MiniMudkipz/dev/PACS/UNDERSEA/undersea-agent/agent/missions/case_study_1/meta_vehicle_alpha.moos";

    UUV.Run(run_command.c_str(), mission_file.c_str());

    return (0);
}

