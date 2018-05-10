package com.mycompany.a3;

import com.codename1.ui.geom.Point;

public interface ISelectable {
  public void setSelected(boolean yesNo);
  public boolean isSelected();
  public boolean contains(Point pPtrRelPrnt);
}
