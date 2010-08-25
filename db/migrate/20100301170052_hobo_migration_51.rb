class HoboMigration51 < ActiveRecord::Migration
  def self.up
    binning_attrs = [

    {:binning_name=>'1x1',:name=>'horizontal',:visibility=>true,:content=> 1.0},
    {:binning_name=>'1x1',:name=>'vertical',:visibility=>true,:content=> 1.0},
    {:binning_name=>'1x2',:name=>'horizontal',:visibility=>true,:content=> 1.0},
    {:binning_name=>'1x2',:name=>'vertical',:visibility=>true,:content=> 2.0},
    {:binning_name=>'2x1',:name=>'horizontal',:visibility=>true,:content=> 2.0},
    {:binning_name=>'2x1',:name=>'vertical',:visibility=>true,:content=> 1.0},
    {:binning_name=>'2x2',:name=>'horizontal',:visibility=>true,:content=> 2.0},
    {:binning_name=>'2x2',:name=>'vertical',:visibility=>true,:content=> 2.0},

    ]

    binning_attrs.each { |m| NodeAttribute.create :name => m.fetch(:name),
      :node => Value.find_by_name(m.fetch(:binning_name)),
      :content => m.fetch(:content),
      :visibility => m.fetch(:visibility)
    }

  end

  def self.down

  end
end
