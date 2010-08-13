class HoboMigration50 < ActiveRecord::Migration
  def self.up
    filter_attrs = [

    #{:filter_name=>'Sloan_u\'',:name=>'lambda(Å)',:visibility=>true,:content=> 305.05},
    #{:filter_name=>'Sloan_u\'',:name=>'fwhm(Å)',:visibility=>true,:content=> 62.30},
    #{:filter_name=>'Sloan_g\'',:name=>'lambda(Å)',:visibility=>true,:content=> 475.80},
    #{:filter_name=>'Sloan_g\'',:name=>'fwhm(Å)',:visibility=>true,:content=> 143.40},
    #{:filter_name=>'Sloan_r\'',:name=>'lambda(Å)',:visibility=>true,:content=> 623.10},
    #{:filter_name=>'Sloan_r\'',:name=>'fwhm(Å)',:visibility=>true,:content=> 142.60},
    #{:filter_name=>'Sloan_i\'',:name=>'lambda(Å)',:visibility=>true,:content=> 770.60},
    #{:filter_name=>'Sloan_i\'',:name=>'fwhm(Å)',:visibility=>true,:content=> 156.40},
    #{:filter_name=>'Sloan_z\'',:name=>'lambda(Å)',:visibility=>true,:content=> 966.20},
    #{:filter_name=>'Sloan_z\'',:name=>'fwhm(Å)',:visibility=>true,:content=> 267.60},
    {:filter_name=>'f643/27',:name=>'lambda(Å)',:visibility=>true,:content=> 643.0},
    {:filter_name=>'f643/27',:name=>'fwhm(Å)',:visibility=>true,:content=> 27},
    {:filter_name=>'f648/28',:name=>'lambda(Å)',:visibility=>true,:content=> 648.0},
    {:filter_name=>'f648/28',:name=>'fwhm(Å)',:visibility=>true,:content=> 28},
    {:filter_name=>'f657/35',:name=>'lambda(Å)',:visibility=>true,:content=> 657.0},
    {:filter_name=>'f657/35',:name=>'fwhm(Å)',:visibility=>true,:content=> 35},
    {:filter_name=>'f680/43',:name=>'lambda(Å)',:visibility=>true,:content=> 680.0},
    {:filter_name=>'f680/43',:name=>'fwhm(Å)',:visibility=>true,:content=> 43},
    {:filter_name=>'f694/44',:name=>'lambda(Å)',:visibility=>true,:content=> 694.0},
    {:filter_name=>'f694/44',:name=>'fwhm(Å)',:visibility=>true,:content=> 44},
    {:filter_name=>'f709/45',:name=>'lambda(Å)',:visibility=>true,:content=> 709.0},
    {:filter_name=>'f709/45',:name=>'fwhm(Å)',:visibility=>true,:content=> 45},
    {:filter_name=>'f723/45',:name=>'lambda(Å)',:visibility=>true,:content=> 723.0},
    {:filter_name=>'f723/45',:name=>'fwhm(Å)',:visibility=>true,:content=> 45},
    {:filter_name=>'f738/46',:name=>'lambda(Å)',:visibility=>true,:content=> 738.0},
    {:filter_name=>'f738/46',:name=>'fwhm(Å)',:visibility=>true,:content=> 46},
    {:filter_name=>'f754/50',:name=>'lambda(Å)',:visibility=>true,:content=> 754.0},
    {:filter_name=>'f754/50',:name=>'fwhm(Å)',:visibility=>true,:content=> 50},
    {:filter_name=>'f770/50',:name=>'lambda(Å)',:visibility=>true,:content=> 770.0},
    {:filter_name=>'f770/50',:name=>'fwhm(Å)',:visibility=>true,:content=> 50},
    {:filter_name=>'f785/48',:name=>'lambda(Å)',:visibility=>true,:content=> 785.0},
    {:filter_name=>'f785/48',:name=>'fwhm(Å)',:visibility=>true,:content=> 48},
    {:filter_name=>'f802/51',:name=>'lambda(Å)',:visibility=>true,:content=> 802.0},
    {:filter_name=>'f802/51',:name=>'fwhm(Å)',:visibility=>true,:content=> 51},
    {:filter_name=>'f819/52',:name=>'lambda(Å)',:visibility=>true,:content=> 819.0},
    {:filter_name=>'f819/52',:name=>'fwhm(Å)',:visibility=>true,:content=> 52},
    {:filter_name=>'f838/58',:name=>'lambda(Å)',:visibility=>true,:content=> 838.0},
    {:filter_name=>'f838/58',:name=>'fwhm(Å)',:visibility=>true,:content=> 58},
    {:filter_name=>'f858/58',:name=>'lambda(Å)',:visibility=>true,:content=> 858.0},
    {:filter_name=>'f858/58',:name=>'fwhm(Å)',:visibility=>true,:content=> 58},
    {:filter_name=>'f878/59',:name=>'lambda(Å)',:visibility=>true,:content=> 878.0},
    {:filter_name=>'f878/59',:name=>'fwhm(Å)',:visibility=>true,:content=> 59},
    {:filter_name=>'f893/50',:name=>'lambda(Å)',:visibility=>true,:content=> 893.0},
    {:filter_name=>'f893/50',:name=>'fwhm(Å)',:visibility=>true,:content=> 50},
    {:filter_name=>'f902/40',:name=>'lambda(Å)',:visibility=>true,:content=> 902.0},
    {:filter_name=>'f902/40',:name=>'fwhm(Å)',:visibility=>true,:content=> 40},
    {:filter_name=>'f910/40',:name=>'lambda(Å)',:visibility=>true,:content=> 910.0},
    {:filter_name=>'f910/40',:name=>'fwhm(Å)',:visibility=>true,:content=> 40},
    {:filter_name=>'f919/41',:name=>'lambda(Å)',:visibility=>true,:content=> 919.0},
    {:filter_name=>'f919/41',:name=>'fwhm(Å)',:visibility=>true,:content=> 41},
    {:filter_name=>'f923/34',:name=>'lambda(Å)',:visibility=>true,:content=> 923.0},
    {:filter_name=>'f923/34',:name=>'fwhm(Å)',:visibility=>true,:content=> 34},
    {:filter_name=>'f927/34',:name=>'lambda(Å)',:visibility=>true,:content=> 927.0},
    {:filter_name=>'f927/34',:name=>'fwhm(Å)',:visibility=>true,:content=> 34},
    {:filter_name=>'f932/34',:name=>'lambda(Å)',:visibility=>true,:content=> 932.0},
    {:filter_name=>'f932/34',:name=>'fwhm(Å)',:visibility=>true,:content=> 34},
    {:filter_name=>'f936/35',:name=>'lambda(Å)',:visibility=>true,:content=> 936.0},
    {:filter_name=>'f936/35',:name=>'fwhm(Å)',:visibility=>true,:content=> 35},
    {:filter_name=>'f940/35',:name=>'lambda(Å)',:visibility=>true,:content=> 940.0},
    {:filter_name=>'f940/35',:name=>'fwhm(Å)',:visibility=>true,:content=> 35},

    ]

    filter_attrs.each { |m| NodeAttribute.create :name => m.fetch(:name),
      :node => Value.find_by_name(m.fetch(:filter_name)),
      :content => m.fetch(:content),
      :visibility => m.fetch(:visibility)
    }

  end

  def self.down

  end
end
