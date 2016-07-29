class AddDefaultHostnameport < ActiveRecord::Migration
  def self.up
    change_column :projects, :hostnameport, :string, :limit => 255, :default => "myhostname:5555"
  end

  def self.down
    change_column :projects, :hostnameport, :string
  end
end
