class HoboMigration26 < ActiveRecord::Migration
  def self.up
    drop_table :scope_kinds
    drop_table :labels
  end

  def self.down
    create_table "scope_kinds", :force => true do |t|
      t.string   "name"
      t.datetime "created_at"
      t.datetime "updated_at"
    end
    
    create_table "labels", :force => true do |t|
      t.string   "name"
      t.datetime "created_at"
      t.datetime "updated_at"
    end
  end
end
