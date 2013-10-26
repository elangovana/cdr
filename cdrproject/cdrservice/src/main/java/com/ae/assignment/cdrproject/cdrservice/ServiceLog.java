package com.ae.assignment.cdrproject.cdrservice;

import java.io.Serializable;

public interface ServiceLog extends Serializable {

	  int retrieveLinesRead(String FileName);

	  void putLinesRead(String FileName, int linesReadSoFar);

}